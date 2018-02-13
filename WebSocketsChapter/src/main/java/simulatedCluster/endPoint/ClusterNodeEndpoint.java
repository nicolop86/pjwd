package simulatedCluster.endPoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simulatedCluster.message.ClusterMessage;

@ServerEndpoint("/clusterNodeSocket/{nodeId}")
public class ClusterNodeEndpoint {

	private static final Log _log = LogFactory.getLog(ClusterNodeEndpoint.class);
	private static final List<Session> nodes = new ArrayList<>(2);

	@OnOpen
	/*
	 * This method notifies to every node in the cluster a new join
	 */
	public void onOpen(Session session, @PathParam("nodeId") String nodeId){
		if(_log.isInfoEnabled()){
			_log.info("Opening connection with server endpoint " + this.getClass().getName());
			_log.info("Node Id " + nodeId + " connected to cluster.");
		}

		ClusterMessage message = new ClusterMessage(nodeId, "Joined the cluster");

		try{
			byte[] bytes = ClusterNodeEndpoint.toByteArray(message);
			for(Session node : ClusterNodeEndpoint.nodes){
				node.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
			}
		} catch(IOException e){
			if(_log.isErrorEnabled()){
				_log.error(e,e);
			}
		}
		ClusterNodeEndpoint.nodes.add(session);
	}

	private static byte[] toByteArray(ClusterMessage message) throws IOException{
		try(ByteArrayOutputStream output = new ByteArrayOutputStream();
				ObjectOutputStream stream = new ObjectOutputStream(output)){
			stream.writeObject(message);
			return output.toByteArray();
		}
	}

	@OnMessage
	/*
	 * This method acts as a dispatcher of incoming message to
	 * all the recipients except for sender
	 */
	public void onMessage(Session session, byte[] message){
		if(_log.isInfoEnabled()){
			_log.info("Server endpoint received a message");
		}
		try{
			for(Session node : ClusterNodeEndpoint.nodes){
				if(node != session){
					node.getBasicRemote().sendBinary(ByteBuffer.wrap(message));
				}
			}
		}catch(IOException e){
			if(_log.isErrorEnabled()){
				_log.error(e,e);
			}
		}
	}
	
	@OnClose
	public void onClose(Session session, @PathParam("nodeId") String nodeId){
		if(_log.isInfoEnabled()){
			_log.info("Node " + nodeId + " disconnected");
			ClusterNodeEndpoint.nodes.remove(session);
			ClusterMessage message = new ClusterMessage(nodeId, "Left the Cluster");
			
			try{
				byte[] bytes = ClusterNodeEndpoint.toByteArray(message);
				for(Session node : ClusterNodeEndpoint.nodes){
					node.getBasicRemote().sendBinary(ByteBuffer.wrap(bytes));
				}
			}catch(IOException e){
				if(_log.isErrorEnabled()){
					_log.error(e,e);
				}
			}
		}
	}
}