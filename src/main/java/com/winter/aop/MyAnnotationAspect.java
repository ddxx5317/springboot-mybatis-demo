package com.winter.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Order(0)
@Aspect
@Component
public class MyAnnotationAspect {


	@Pointcut("@annotation(com.winter.aop.MyAnnotation)")
	public void i18nPointCut() {}

	@Around("i18nPointCut() &&  @annotation(myAnnotation)")
	public Object doAround(ProceedingJoinPoint point , MyAnnotation myAnnotation) throws Throwable{

		String businessIdSpel = myAnnotation.name();
		Object[] args = point.getArgs();
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		//获取被拦截方法参数名列表(使用Spring支持类库)
		LocalVariableTableParameterNameDiscoverer localVariableTable = new LocalVariableTableParameterNameDiscoverer();
		String[] paraNameArr = localVariableTable.getParameterNames(method);

		//SPEL上下文
		StandardEvaluationContext context = new StandardEvaluationContext();
		//把方法参数放入SPEL上下文中
		for(int i=0;i<paraNameArr.length;i++) {
			context.setVariable(paraNameArr[i], args[i]);
		}
		String businessId = null;
		// 使用变量方式传入业务动态数据
		if(businessIdSpel.matches("^#.*.$")) {
			//使用SPEL进行key的解析
			ExpressionParser parser = new SpelExpressionParser();
			businessId = parser.parseExpression(businessIdSpel).getValue(context, String.class);
		}
		System.out.println(businessId);

		return point.proceed();
	}
}
