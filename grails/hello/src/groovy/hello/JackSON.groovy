package hello

import javax.servlet.http.HttpServletResponse

import org.codehaus.groovy.grails.web.converters.AbstractConverter
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException
import org.codehaus.groovy.grails.web.converters.marshaller.ObjectMarshaller

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

class JackSON extends AbstractConverter {
	Object target
	final static ObjectMapper mapper = new ObjectMapper()

	static {
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
		mapper.configure(JsonGenerator.Feature.FLUSH_PASSED_TO_STREAM, false)
		mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT, false)
	}

	public JackSON() {
	}

	public JackSON(Object target) {
		setTarget(target)
	}

	public void render(Writer out) throws ConverterException {
		try {
			JsonGenerator jsonGenerator =
					mapper.getFactory().createGenerator(out);
			mapper.writeValue(jsonGenerator, target)
		} catch(e) {
			throw new ConverterException(e)
		}

		try {
			out.flush()
			out.close()
		} catch (Exception e) {
			log.warn("Unexpected exception while closing a writer: " + e.getMessage())
		}
	}

	public void render(HttpServletResponse response) throws ConverterException {
		response.setContentType("application/json;charset=UTF-8");
		try {
			render(response.getWriter())
		} catch (IOException e) {
			throw new ConverterException(e)
		}
	}

	public Object getWriter() throws ConverterException {
		throw new ConverterException("Not Implemented")
	}

	public void convertAnother(Object o) throws ConverterException {
		throw new ConverterException("Not Implemented")
	}

	public void build(Closure c) throws ConverterException {
		throw new ConverterException("Not Implemented")
	}

	public ObjectMarshaller lookupObjectMarshaller(Object target) {
		return null
	}

	public void setTarget(Object target) {
		this.target = target
	}
}
