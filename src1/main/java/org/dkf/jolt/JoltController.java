package org.dkf.jolt;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dkf.jolt.exceptions.InternalError;
import org.dkf.jolt.exceptions.MissedParameters;
import org.dkf.jolt.model.JoltExample;
import org.dkf.jolt.model.TransformRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JoltController {
	Logger LOG = LoggerFactory.getLogger(JoltController.class);
	private static final String INPUT = "input";
	private static final String SPEC = "spec";
	private static final String SORT = "sort";


	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ObjectMapper objectMapperSorted;

	@Autowired
	private List<JoltExample> registeredExamples;

	@GetMapping("/")
	public String joltdemo(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
		LOG.info("JOLT demo has been started");
		model.addAttribute("examples", registeredExamples.stream().filter(x -> "native".equals(x.type)).collect(Collectors.toList()));
		model.addAttribute("examples_r", registeredExamples.stream().filter(x -> "reltio".equals(x.type)).collect(Collectors.toList()));
		return "jolt_demo";
	}

	@PostMapping(path = "/transform",
			consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public String transform(@RequestParam MultiValueMap paramMap)  {
		if (!paramMap.containsKey(INPUT) || !paramMap.containsKey(SPEC) || !paramMap.containsKey(SORT)) {
			LOG.error("missed required parameters");
			throw new MissedParameters("Missed input parameter, requires: " + SPEC + ", " + INPUT + ", " + SORT);
		}

		try {
			Object spec = JsonUtils.jsonToObject(paramMap.getFirst(SPEC).toString());
			boolean sort = Boolean.parseBoolean(paramMap.getFirst(SORT).toString());
			Object input = objectMapper.readValue(paramMap.getFirst(INPUT).toString(), Object.class);
			Chainr chainr = Chainr.fromSpec(spec);
			Object res = chainr.transform(input);
			return sort ? objectMapperSorted.writeValueAsString(res) : objectMapper.writeValueAsString(res);
		} catch (Exception e) {
			throw new InternalError(e.getMessage());
		}
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public String handleError(HttpServletRequest req, Exception ex) {
		LOG.error("Request: " + req.getRequestURL() + " raised " + ex);
		// always return 200/OK to show error message in text area
		return "Can not transform: " + ex.getMessage();
	}
}
