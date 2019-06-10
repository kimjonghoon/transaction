package net.java_school.commons;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class TestLogger {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterReturning("execution(* net.java_school.bank.BankDao.deposit(..))")
	public void depositLog(JoinPoint point) {
		Object[] a = point.getArgs();
		String accountNo = (String) a[0];
		Long amount = (Long) a[1];
		String methodName = point.getSignature().getName();
		logger.debug("{}|{}|{}", methodName, accountNo, amount);
	}

	@AfterReturning("execution(* withdraw(..)) && args(accountNo, amount)")
	public void withdrawLog(String accountNo, long amount) {
		logger.debug("WITHDRAW|{}|{}", accountNo, amount);
	}

}