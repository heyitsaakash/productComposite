package com.bbb.composite.product.details.dto.product;

import java.io.Serializable;

/**
 * MediaDTO attributes specified here.
 *
 * @author psh111
 */
public class MediaDTO implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -901978165892729571L;

	private String mediaType;

	private String providerId;

	private String mediaSource;

	private String mediaDescription;

	private String comments;

	private String mediaTranscript;

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getMediaSource() {
		return mediaSource;
	}

	public void setMediaSource(String mediaSource) {
		this.mediaSource = mediaSource;
	}

	public String getMediaDescription() {
		return mediaDescription;
	}

	public void setMediaDescription(String mediaDescription) {
		this.mediaDescription = mediaDescription;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMediaTranscript() {
		return mediaTranscript;
	}

	public void setMediaTranscript(String mediaTranscript) {
		this.mediaTranscript = mediaTranscript;
	}
	
	
}
