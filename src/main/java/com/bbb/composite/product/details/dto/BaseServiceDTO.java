package com.bbb.composite.product.details.dto;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanAccessor;

import com.bbb.core.dto.ErrorMessage;
import com.bbb.core.dto.ServiceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * The Class BaseServiceDTO.
 *
 * @author Viresh Wali
 * @param <T> the generic type
 */
public class BaseServiceDTO<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6735494474548015978L;

	/** The Constant formatter. */
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-uuuu HH:mm:ss:SSS")
			.withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault());

	/** The service status. */
	protected ServiceStatus serviceStatus = ServiceStatus.SUCCESS;

	/** The error messages. */
	private List<ErrorMessage> errorMessages = new ArrayList<>();

	/** The data. */
	@SuppressWarnings("squid:S1948")
	@JsonProperty(value="response")
	private T data;

	
	
	/** The trace id. */
	private String traceId;

	/** The span id. */
	private String spanId;
	
	/** The request received time. */
	private Instant requestReceivedTime;
	
	/** The response sent time. */
	private Instant responseSentTime;

	/** The req readable time. */
	private String reqReadableTime;

	/** The resp readable time. */
	private String respReadableTime;

	/** The page size. */
	private int pageSize;

	/** The page from. */
	private int pageFrom;

	/** The page to. */
	private int pageTo;

	/**
	 * Gets the service status.
	 *
	 * @return the service status
	 */
	public ServiceStatus getServiceStatus() {
		return serviceStatus;
	}

	/**
	 * Sets the service status.
	 *
	 * @param serviceStatus the new service status
	 */
	public void setServiceStatus(ServiceStatus serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public T getData() {
		return data;
	}

/**
	 * Sets the data.
	 * 	 *
	 * @param data the new data
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	

	/**
	 * Gets the request received time.
	 *
	 * @return the request received time
	 */
	public Instant getRequestReceivedTime() {
		return requestReceivedTime;
	}

	/**
	 * Sets the request received time.
	 *
	 * @param requestReceivedTime the new request received time
	 */
	public void setRequestReceivedTime(Instant requestReceivedTime) {
		this.requestReceivedTime = requestReceivedTime;
		setReqReadableTime();
		
	}

	/**
	 * Gets the response sent time.
	 *
	 * @return the response sent time
	 */
	public Instant getResponseSentTime() {
		return responseSentTime;
	}

	/**
	 * Sets the response sent time.
	 *
	 * @param responseSentTime the new response sent time
	 */
	public void setResponseSentTime(Instant responseSentTime) {
		this.responseSentTime = responseSentTime;
		setRespReadableTime();
	}

	/**
	 * Gets the page size.
	 *
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the page size.
	 *
	 * @param pageSize the new page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Gets the page from.
	 *
	 * @return the page from
	 */
	public int getPageFrom() {
		return pageFrom;
	}

	/**
	 * Sets the page from.
	 *
	 * @param pageFrom the new page from
	 */
	public void setPageFrom(int pageFrom) {
		this.pageFrom = pageFrom;
	}

	/**
	 * Gets the page to.
	 *
	 * @return the page to
	 */
	public int getPageTo() {
		return pageTo;
	}

	/**
	 * Sets the page to.
	 *
	 * @param pageTo the new page to
	 */
	public void setPageTo(int pageTo) {
		this.pageTo = pageTo;
	}

	/**
	 * Gets the error messages.
	 *
	 * @return the error messages
	 */
	public List< ErrorMessage> getErrorMessages() {
		return Collections.unmodifiableList(errorMessages);
	}

	/** 
	 * For appending error Message
	 * @param errorMessage
	 */
	public void appendErrorMessage(ErrorMessage errorMessage) {
		if (null != errorMessage) {
			this.errorMessages.add(errorMessage);
		}
	}
	
	/**
	 * Adds the error message.
	 *
	 * @param errorMessagesToBeAdded the error messages
	 */
	public void addErrorMessages(List<? extends ErrorMessage> errorMessagesToBeAdded) {
		if (CollectionUtils.isNotEmpty(errorMessagesToBeAdded)) {
			this.errorMessages.addAll(errorMessagesToBeAdded);
		}
	}
	
	/**
	 * Gets the trace id.
	 *
	 * @return the trace id
	 */
	public String getTraceId() {
		return traceId;
	}

	/**
	 * Sets the trace id.
	 *
	 * @param traceId the new trace id
	 */
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	/**
	 * Gets the span id.
	 *
	 * @return the span id
	 */
	public String getSpanId() {
		return spanId;
	}

	/**
	 * Sets the span id.
	 *
	 * @param spanId the new span id
	 */
	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}



	/**
	 * Adds the trace info.
	 *
	 * @param spanAccessor the span accessor
	 */
	public void addTraceInfo(SpanAccessor spanAccessor) {
		if (null != spanAccessor) {
			Span span = spanAccessor.getCurrentSpan();
			addTraceInfo(span);
		}
	}
	
	/**
	 * Adds the trace info.
	 * @param span
	 */
	public void addTraceInfo(Span span) {
		if (null != span) {
			String hexTraceId = Span.idToHex(span.getTraceId());
			String hexSpanId = Span.idToHex(span.getSpanId());
			this.setTraceId(hexTraceId);
			this.setSpanId(hexSpanId);
		}
	}

	
	
	

	/**
	 * Gets the req readable time.
	 *
	 * @return the req readable time
	 */
	public String getReqReadableTime() {
		return reqReadableTime;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BaseServiceDTO [serviceStatus=" + serviceStatus + ", errorMessages=" + errorMessages + ", data=" + data
				+ ", traceId=" + traceId + ", spanId=" + spanId + ", requestReceivedTime=" + requestReceivedTime
				+ ", responseSentTime=" + responseSentTime + ", reqReadableTime=" + reqReadableTime
				+ ", respReadableTime=" + respReadableTime + ", pageSize=" + pageSize + ", pageFrom=" + pageFrom
				+ ", pageTo=" + pageTo + "]";
	}

	/**
	 * Sets the req readable time.
	 */
	public void setReqReadableTime() {
		this.reqReadableTime = formatter.format(getRequestReceivedTime());
	}

	/**
	 * Gets the resp readable time.
	 *
	 * @return the resp readable time
	 */
	public String getRespReadableTime() {
		return respReadableTime;
	}

	/**
	 * Sets the resp readable time.
	 */
	public void setRespReadableTime() {
		this.respReadableTime = formatter.format(getResponseSentTime());
	}
}
