package dealer.customer.controller.error;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import dealer.customer.controller.error.GlobalControllerErrorHandler;


@RestControllerAdvice
@Slf4j
public class GlobalControllerErrorHandler {
	private enum LogStatus {
		STACK_TRACE, MESSAGE_ONLY
	}
		
	@Data
	private class ExceptionMessage {
		private String message;
		private String statusReason;
		private int statusCode;
		private String timestamp;
		private String uri;
	}
	
	/**************************************************************
	*             NoSuchElementException Error Handler
	*************************************************************/
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionMessage handleNoSuchElementException(NoSuchElementException ex,
			WebRequest webRequest)
	{
		return buildExceptionMessage(ex, HttpStatus.NOT_FOUND, webRequest, LogStatus.MESSAGE_ONLY);
	}
	
	/**************************************************************
	*             UnsupportedOperationException Error Handler
	*************************************************************/
	@ExceptionHandler(UnsupportedOperationException.class)
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	public ExceptionMessage handleUnsupportedOperationException(UnsupportedOperationException ex,
			WebRequest webRequest)
	{
		return buildExceptionMessage(ex, HttpStatus.METHOD_NOT_ALLOWED, webRequest, LogStatus.MESSAGE_ONLY);
	}
	
	/**************************************************************
	*             IllegalArgumentException Error Handler
	*************************************************************/
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	public ExceptionMessage handleIllegalArgumentException(IllegalArgumentException ex,
			WebRequest webRequest)
	{
		return buildExceptionMessage(ex, HttpStatus.NOT_ACCEPTABLE, webRequest, LogStatus.MESSAGE_ONLY);
	}
	
	/****************************************************************
	 *              NullPointerException Error Handler
	****************************************************************/
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionMessage handleNullPointerException(NullPointerException ex,
			WebRequest webRequest)
	{
		return buildExceptionMessage(ex, HttpStatus.INTERNAL_SERVER_ERROR, webRequest, LogStatus.MESSAGE_ONLY);
	}
	
	/****************************************************************
	 *             DataIntegrityViolationException  
	****************************************************************/
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ExceptionMessage handleDataIntegrityViolationException(DataIntegrityViolationException ex,
			WebRequest webRequest)
	{
		return buildExceptionMessage(ex, HttpStatus.CONFLICT, webRequest, LogStatus.MESSAGE_ONLY);
	}
	
	
	/****************************************************************
	*            SQLIntegrityConstraintViolationException  
	****************************************************************/
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ExceptionMessage handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex,
			WebRequest webRequest)
	{
		return buildExceptionMessage(ex, HttpStatus.CONFLICT, webRequest, LogStatus.MESSAGE_ONLY);
	}
	
	/**************************************************************
	*             buildExceptionMessage Method     
	*************************************************************/	
	private ExceptionMessage buildExceptionMessage(Exception ex,
			HttpStatus status, WebRequest webRequest, LogStatus logStatus) {
		String message = ex.toString();
		String statusReason = status.getReasonPhrase();
		int statusCode = status.value();
		String uri = null;
		String timestamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);
			
		if(webRequest instanceof ServletWebRequest swr) {
			uri = swr.getRequest().getRequestURI();
		}
			
		if(logStatus == LogStatus.MESSAGE_ONLY) {
			log.error("Exception: {}", ex.toString());
		}
		else {
			log.error("Exception: ", ex);
		}
			
		ExceptionMessage excMsg = new ExceptionMessage();
		
		excMsg.setMessage(message);
		excMsg.setStatusCode(statusCode);
		excMsg.setStatusReason(statusReason);
		excMsg.setTimestamp(timestamp);
		excMsg.setUri(uri);
			
		return excMsg;
		
	}


}
