package com.EM2.MoneyTransferApplication.controller.exception;

import java.sql.SQLException;

import javax.naming.CommunicationException;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(GlobalExceptionController.class);
	
	@ExceptionHandler(value=MethodArgumentTypeMismatchException.class)
	public ModelAndView handleThisException(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	
	@ExceptionHandler(value=NullPointerException.class)
	public ModelAndView handleNullPointerEx(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	
	@ExceptionHandler(value = SQLException.class)
	public ModelAndView handleSQLEx(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	
	@ExceptionHandler(value = CannotGetJdbcConnectionException.class)
	public ModelAndView handleJdbcConnectionEx(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ModelAndView handleDataIntegrityEx(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	
	@ExceptionHandler(value = CommunicationException.class)
	public ModelAndView handlecommunicationEx(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	
	@ExceptionHandler(value = NumberFormatException.class)
	public ModelAndView handleNumberFormatEx(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	
	@ExceptionHandler(value = DataAccessException.class)
	public ModelAndView handleDataAccessEx(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	
	@ExceptionHandler(value = DataAccessResourceFailureException.class)
	public ModelAndView handleDataAccessResourceFailureEx(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
	@ExceptionHandler(value = Exception.class)
	public ModelAndView handleException(Exception e) {
		logger.error(e.toString());
		return new ModelAndView("Exception").addObject("exceptionMessage",e.toString());
	}
}
