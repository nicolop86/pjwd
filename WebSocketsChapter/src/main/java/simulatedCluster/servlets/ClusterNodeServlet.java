package simulatedCluster.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simulatedCluster.message.ClusterMessage;

@ClientEndpoint
public class ClusterNodeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Log _log = LogFactory.getLog(ClusterNodeServlet.class);
	private Session session;
	private String nodeId;

	@Override
	public void init() throws ServletException{
		if(_log.isInfoEnabled()){
			_log.info(this.getServletName() + " is starting ...");
		}
		this.nodeId = this.getInitParameter("nodeId");
		String path = this.getServletContext().getContextPath() + "/clusterNodeSocket/" + this.nodeId;
		try{
			URI uri = new URI("ws", "localhost:8080", path, null, null);
			this.session = ContainerProvider.getWebSocketContainer().connectToServer(this, uri);
		}catch(URISyntaxException | IOException | DeploymentException e){
			throw new ServletException("Cannot connect to " + path + ".", e);
		}
	}

	@Override
	public void destroy() {
		if(_log.isInfoEnabled()){
			_log.info("... " + this.getServletName() + " is going to be destroyed");
		}
		try{
			this.session.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		if(_log.isInfoEnabled()){
			_log.info(this.getServletName() + " is starting doGet method");
			_log.info("Creating ClusterMessage with parameters nodeId : " + this.nodeId + " and message: " + 
					"request:{ip:\"" + req.getRemoteAddr() + "\", queryString:\"" + req.getQueryString() + "\"}");
		}
		ClusterMessage message = new ClusterMessage(this.nodeId, "request:{ip:\"" + req.getRemoteAddr() + "\", queryString:\"" + req.getQueryString() + "\"}");

		try(OutputStream output = this.session.getBasicRemote().getSendStream();
				ObjectOutputStream stream = new ObjectOutputStream(output)){
			stream.writeObject(message);
		}
		resp.getWriter().append("OK");
	}
	
	@OnMessage
	public void onMessage(InputStream in){
		if(_log.isInfoEnabled()){
			_log.info(this.getServletName() + " triggering onMessage method...");
		}
		try(ObjectInputStream stream = new ObjectInputStream(in)){
			ClusterMessage message = (ClusterMessage) stream.readObject();
			if(_log.isInfoEnabled()){
				_log.info(this.getServletName() + " received message: " + 
			message.getMessage() + " from node: " + message.getNodeId());
			}
		} catch (IOException | ClassNotFoundException e) {
			if(_log.isErrorEnabled()){
				_log.error(e,e);
			}
		}
	}
	
	@OnClose
	public void onClose(CloseReason reason){
		CloseReason.CloseCode code = reason.getCloseCode();
		if(code != CloseReason.CloseCodes.NORMAL_CLOSURE){
			if(_log.isErrorEnabled()){
				_log.error("ERROR: connection closed unexpectedly with code " + code + " due to " + reason.getReasonPhrase());
			}
		}
	}

}
