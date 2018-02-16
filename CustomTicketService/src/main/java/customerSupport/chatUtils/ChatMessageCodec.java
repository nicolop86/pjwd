package customerSupport.chatUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatMessageCodec implements Encoder.BinaryStream<ChatMessage>, Decoder.BinaryStream<ChatMessage>{

	private static final Log _log = LogFactory.getLog(ChatMessageCodec.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.findAndRegisterModules();
		MAPPER.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
	}

	@Override
	public ChatMessage decode(InputStream inputStream) throws DecodeException, IOException {
		if(_log.isInfoEnabled()){
			_log.info("Codec calling method decode");
		}
		try {
			return ChatMessageCodec.MAPPER.readValue(inputStream, ChatMessage.class);
		} catch(JsonParseException | JsonMappingException e) {
			throw new DecodeException((ByteBuffer)null, e.getMessage(), e);
		}
	}

	@Override
	public void encode(ChatMessage chatMessage, OutputStream outputStream) throws EncodeException, IOException {
		if(_log.isInfoEnabled()){
			_log.info("Codec calling method encode");
		}
		try {
			if(_log.isInfoEnabled()){
				_log.info("Message inside codec is: " + chatMessage.getContent());
			}
			
			ChatMessageCodec.MAPPER.writeValue(outputStream, chatMessage);
		} catch(JsonGenerationException | JsonMappingException e) {
			throw new EncodeException(chatMessage, e.getMessage(), e);
		}
	}

	@Override
	public void destroy() {
		if(_log.isInfoEnabled()){
			_log.info("Codec is going to be destroyed");
		}
	}

	@Override
	public void init(EndpointConfig arg0) {
		if(_log.isInfoEnabled()){
			_log.info("Initializing Codec");
		}
	}

}
