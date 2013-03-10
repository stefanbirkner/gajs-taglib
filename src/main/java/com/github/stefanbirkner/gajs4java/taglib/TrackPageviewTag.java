package com.github.stefanbirkner.gajs4java.taglib;

import static com.github.stefanbirkner.gajs4java.core.model.CommandForTrackerUtil.wrapCommandsForDefaultTracker;
import static com.github.stefanbirkner.gajs4java.core.model.Protocol.DECIDE_BY_JAVASCRIPT;
import static com.github.stefanbirkner.gajs4java.core.util.ProtocolUtil.finalProtocolForRequestAndBaseProtocol;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.github.stefanbirkner.gajs4java.core.model.CommandForTracker;
import com.github.stefanbirkner.gajs4java.core.model.Protocol;
import com.github.stefanbirkner.gajs4java.core.model.SetAccount;
import com.github.stefanbirkner.gajs4java.core.model.TrackPageview;
import com.github.stefanbirkner.gajs4java.core.model.TrackingConfiguration;
import com.github.stefanbirkner.gajs4java.core.render.TrackingConfigurationRenderer;

public class TrackPageviewTag extends SimpleTagSupport {
	private static final TrackingConfigurationRenderer RENDERER = new TrackingConfigurationRenderer();
	private Protocol protocol = DECIDE_BY_JAVASCRIPT;
	private String account;
	private String pageUrl;

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}

	@Override
	public void doTag() throws IOException {
		Protocol finalProtocol = finalProtocolForRequestAndBaseProtocol(
				request(), protocol);
		TrackingConfiguration configuration = new TrackingConfiguration(
				commands(), finalProtocol);
		Writer writer = getJspContext().getOut();
		RENDERER.writeTrackingConfigurationToWriter(configuration, writer);
	}

	private List<CommandForTracker> commands() {
		TrackPageview trackPageview = (pageUrl == null) ? new TrackPageview()
				: new TrackPageview(pageUrl);
		return wrapCommandsForDefaultTracker(new SetAccount(account),
				trackPageview);
	}

	private ServletRequest request() {
		return ((PageContext) getJspContext()).getRequest();
	}
}
