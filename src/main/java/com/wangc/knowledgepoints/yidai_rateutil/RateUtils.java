package com.wangc.knowledgepoints.yidai_rateutil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 2018-02-09
 * @author wangxin
 *
 */
public class RateUtils {

	/**
	 * 等额本金
	 * https://baike.baidu.com/item/%E7%AD%89%E9%A2%9D%E6%9C%AC%E9%87%91/9435494
	 * @param totalInterest   总利息
	 * @param period   总期数
	 * @param principal  总本金
	 * @return
	 */
	public static BigDecimal getDEBJRate(BigDecimal totalInterest,BigDecimal period,BigDecimal principal){
		//还款总利息=（还款月数+1）×贷款额×月利率/2
		BigDecimal rate = new  BigDecimal(0);
		rate  = new BigDecimal(12).multiply(new BigDecimal(2)).multiply(totalInterest).divide((period.add(new BigDecimal(1))).multiply(principal),4,RoundingMode.HALF_UP);
		return rate;
	}
	
	
	
	/**
	 * 等额本息
	 * https://baike.baidu.com/item/%E7%AD%89%E9%A2%9D%E6%9C%AC%E6%81%AF%E8%BF%98%E6%AC%BE%E6%B3%95/8040344?fr=aladdin
	 * @param monthRepayAmt  每月还款额
	 * @param principal      总本金  log2N
	 * @param period         总还款期数
	 */
	public static BigDecimal getDEBXRate(BigDecimal monthRepayAmt,BigDecimal period,BigDecimal principal){
		if(monthRepayAmt==null || period==null || principal==null){
			throw new RuntimeException("rateUtils，反算等额本息，入参不能为空");
		}
		if(monthRepayAmt.multiply(period).add(new BigDecimal(0.1)).compareTo(principal) <0){
			throw new RuntimeException("入参每月还款额*总期数不能小于应还金额");
		}
		BigDecimal min = new BigDecimal(0);
		BigDecimal max = new BigDecimal(1);
		BigDecimal mid = new BigDecimal(0);
		BigDecimal midMonthRepayAmt = new BigDecimal(0);
		int times =0;
		while(!(Math.abs(monthRepayAmt.subtract(midMonthRepayAmt).doubleValue())<=0.2)){
			
			mid = min.add(max).divide(new BigDecimal(2), RETENTION_LENGTH, RoundingMode.HALF_UP);
			midMonthRepayAmt = principal.multiply(mid).multiply(new BigDecimal(Math.pow(new BigDecimal(1).add(mid).doubleValue(), period.doubleValue())))//
					.divide(new BigDecimal(Math.pow(new BigDecimal(1).add(mid).doubleValue(), period.doubleValue())).subtract(new BigDecimal(1)),RETENTION_LENGTH,RoundingMode.HALF_UP);
			//System.out.println("rate="+mid + "  midMonthRepayAmt="+midMonthRepayAmt+ "  monthRepayAmt="+monthRepayAmt);
			if(midMonthRepayAmt.compareTo(monthRepayAmt)>0){
				max = mid;
			}else if(midMonthRepayAmt.compareTo(monthRepayAmt)<0){
				min = mid;
			}
			times++;
			if(times>1000){
				break;
			}
		}
		return mid.multiply(new BigDecimal(12)).setScale(4, RoundingMode.HALF_UP);
	}
	
	
	/**
	 * 一次性还本付息
	 * 利息 = (本金*年华利率*计息天数)/365
	 * @param totalInterest  总利息
	 * @param principal      总本金
	 * @param days           总天数
	 * @return
	 */
	public static BigDecimal getLSBQRate(BigDecimal totalInterest,BigDecimal days,BigDecimal principal,String calTotalCalDay){
		BigDecimal rate = new BigDecimal(0);
		rate = totalInterest.multiply(new BigDecimal(calTotalCalDay)).divide(principal.multiply(days), 4, RoundingMode.HALF_UP);
		return rate;
	}
	
	public static void main(String[] args) {
		//10.00,总期数3,总利息16.69,本金1000.00
		System.out.println(RateUtils.getDEBXRate(new BigDecimal(578.764444), new BigDecimal(9), new BigDecimal(5000.00)));
		
		System.out.println((new BigDecimal(0.0992-0.09855).setScale(8, BigDecimal.ROUND_HALF_EVEN)));
		
//		System.out.println(RateUtils.getDEBXRate(new BigDecimal(188.25), new BigDecimal(60), new BigDecimal(10000)));
//		
//		System.out.println(RateUtils.getLSBQRate(new BigDecimal(1000), new BigDecimal(365), new BigDecimal(10000)));
	}
	
	/**保留8位小数*/
	public static final int RETENTION_LENGTH=8;
	/**精度万分之一*/
	public static final BigDecimal PRECISION=new BigDecimal(0.0001);



    public static BigDecimal getDEBXRateNew(BigDecimal benJinAmt,String calDay,int term,BigDecimal toPayAmt,String yearDay ) {

		long startTime = System.currentTimeMillis();
		//BigDecimal benJinAmt = new BigDecimal(40300);//本金
		BigDecimal maxYearRate = new BigDecimal(2.000000);//大 年利率
		BigDecimal minYearRate = new BigDecimal(0.000001);//小 年利率
		BigDecimal yearRate = new BigDecimal(0.000000);//年利率
		//String calDay = "20190524";//第一期起息日
		BigDecimal calTotalCalDay = new BigDecimal(yearDay);
		//int term  = 18;//期数
		//BigDecimal toPayAmt = new BigDecimal(4220.91);//目标应还金额


		BigDecimal calToPayAmt = new BigDecimal(0.000000);
		int n = 1;

		// 已知  贷款本金  贷款期数    应还总额（应还利息+手续费）  推  年化利率
		while(true && n < 21){

			System.out.println("开始循环,次数：" + n);
			System.out.println("最大年利率:" + maxYearRate.toPlainString());
			System.out.println("最小年利率:" + minYearRate.toPlainString());
			System.out.println();
			yearRate = maxYearRate.add(minYearRate).divide(new BigDecimal(2.000000),10,RoundingMode.HALF_UP);

			calToPayAmt = getTotalCalToPayAmt(benJinAmt,calDay,yearRate,calTotalCalDay,term);

			if(toPayAmt.compareTo(calToPayAmt)<=0){
				maxYearRate = yearRate;
			}else{
				minYearRate = yearRate;
			}

			if(toPayAmt.subtract(calToPayAmt).abs().compareTo(new BigDecimal(0.200000))<=0){
				break;
			}
			n++;
		}
		System.out.println("反推的年利率:"+yearRate);
		System.out.println("计算出来的应还总额:"+calToPayAmt);
		long endTime = System.currentTimeMillis();
		System.out.println("反推时间(毫秒):"+(endTime-startTime));
		return yearRate;
    }

	/**
	 * 获取总的应还总额（应还利息+手续费）
	 * @param benJinAmt			本金
	 * @param calDay			计息天数
	 * @param yearRate			年化利率
	 * @param calTotalCalDay	一年总天数
	 * @param term				期供总期数
	 * @return
	 */
	public static BigDecimal getTotalCalToPayAmt(BigDecimal benJinAmt,String calDay,
												 BigDecimal yearRate, BigDecimal calTotalCalDay,int term){

		// 应还总额（应还利息+手续费）
		BigDecimal totalToPayAmt = new BigDecimal(0);

		// 获取每期对应的天数
		List<Long> days = getDaysOfList(calDay,term);
		for (Long day : days) {
			System.out.println(day.intValue());
		}

		// 计算每月本息（不包括最后一期）  = 应还本金 + 应还利息
		BigDecimal monRate = yearRate.divide(new BigDecimal(12.000000),6,RoundingMode.HALF_UP);
		BigDecimal commonNum = new BigDecimal(1).add(monRate).pow(term);
		BigDecimal upNum = benJinAmt.multiply(monRate).multiply(commonNum).setScale(10, RoundingMode.HALF_UP);
		BigDecimal downNum = commonNum.subtract(new BigDecimal(1)).setScale(10, RoundingMode.HALF_UP);
		BigDecimal perTermPayAmt = upNum.divide(downNum,6,RoundingMode.HALF_UP);

		// 计算第1期应还利息 （贷款本金*计息天数*年化利率/一年总天数）
		BigDecimal lixiAmt_1 = benJinAmt.multiply(new BigDecimal(days.get(0))).multiply(yearRate).divide(calTotalCalDay,6,RoundingMode.HALF_UP);

		// 计算第1期应还本金
		BigDecimal origAmt_1 = perTermPayAmt.subtract(lixiAmt_1);
		// 计算第1期剩余本金
		BigDecimal lastesBenJinAmt = benJinAmt.subtract(origAmt_1);
		// 更新 应还总额（应还利息+手续费）
		totalToPayAmt = totalToPayAmt.add(lixiAmt_1);

		// 从第2期开始计算
		for(int i =1;i<term;i++){
			// 计算第i期的应还利息
			BigDecimal lixiAmt = lastesBenJinAmt.multiply(new BigDecimal(days.get(i))).multiply(yearRate).divide(calTotalCalDay,6,RoundingMode.HALF_UP);
			// 计算第i期应还本金
			BigDecimal origAmt = perTermPayAmt.subtract(lixiAmt);
			lastesBenJinAmt = lastesBenJinAmt.subtract(origAmt);
			// 更新 应还总额（应还利息+手续费）
			totalToPayAmt = totalToPayAmt.add(lixiAmt);
		}
		return totalToPayAmt;
	}

	public static Calendar getDateOfLastMonth(Calendar date, int startDay) {

		Calendar lastDate = (Calendar) date.clone();
		lastDate.add(Calendar.MONTH, 1);
		int maxMonthDay = lastDate.getActualMaximum(Calendar.DAY_OF_MONTH);//新月份的最大日期
		int lastDay = maxMonthDay < startDay ? maxMonthDay : startDay; //如果当前月的最后一天，在下个月不存在，则取下个月的最后一天
		lastDate.set(Calendar.DAY_OF_MONTH, lastDay);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(lastDate.getTime()));
		return lastDate;
	}

	public static Calendar getDateOfLastMonth(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = sdf.parse(dateStr);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			return c;
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format(yyyyMMdd): " + dateStr);
		}
	}

	public static List<Long> getDaysOfList(String dateStr, int term){

		List<Long> days = new ArrayList<Long>();
		Calendar cal = getDateOfLastMonth(dateStr);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		List<Calendar> cals = new ArrayList<Calendar>();
		cals.add(cal);
		for(int i = 0;i<term;i++){
			try {
				Calendar calTmp = (Calendar)cals.get(cals.size()-1).clone();
				Calendar calNext = getDateOfLastMonth(calTmp, cal.get(Calendar.DAY_OF_MONTH));
				cals.add(calNext);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		for(int j = 0;j<cals.size()-1;j++){
			long l = ((Calendar)cals.get(j+1)).getTimeInMillis()-((Calendar)cals.get(j)).getTimeInMillis();
			long day=l/(24*60*60*1000);
			days.add(day);
		}
		return days;
	}

}
