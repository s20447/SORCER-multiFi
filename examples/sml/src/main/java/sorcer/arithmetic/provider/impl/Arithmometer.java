package sorcer.arithmetic.provider.impl;

import groovy.lang.GroovyShell;
import sorcer.arithmetic.provider.Adder;
import sorcer.arithmetic.provider.Divider;
import sorcer.arithmetic.provider.Multiplier;
import sorcer.arithmetic.provider.Subtractor;
import sorcer.core.SorcerConstants;
import sorcer.core.context.ArrayContext;
import sorcer.core.context.Contexts;
import sorcer.core.context.PositionalContext;
import sorcer.core.context.ServiceContext;
import sorcer.service.Context;
import sorcer.service.ContextException;
import sorcer.service.Signature.ReturnPath;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static sorcer.eo.operator.path;
import static sorcer.eo.operator.revalue;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Arithmometer implements SorcerConstants, Serializable {
    private static final long serialVersionUID = -82252627979263L;
	
	public static final String ADD = "add";

	public static final String SUBTRACT = "subtract";

	public static final String MULTIPLY = "multiply";

	public static final String DIVIDE = "divide";
	
	public static final String AVERAGE = "average";

	public static final String EXPRESSION = "expression";

	public static final String RESULT_PATH = "result/value";
			
	public final static Logger logger = LoggerFactory.getLogger(Arithmometer.class
			.getName());

	/**
	 * Implements the {@link Adder} interface.
	 * 
	 * @param context
	 *            input context for this operation
	 * @return an output service context
	 * @throws RemoteException
	 * @throws ContextException 
	 */
	public Context add(Context context) throws RemoteException, ContextException {
		if (context instanceof ArrayContext) {
			return calculateFromArrayContext(context, ADD);
		} else {
			return calculateFromPositionalContext(context, ADD);
		}
	}

	/**
	 * Implements the {@link Subtractor} interface.
	 * 
	 * @param context
	 *            input context for this operation
	 * @return an output service context
	 * @throws RemoteException
	 * @throws ContextException 
	 */
	public Context subtract(Context context)
			throws RemoteException, ContextException {
		if (context instanceof ArrayContext) {
			return calculateFromArrayContext(context, SUBTRACT);
		} else {
			return calculateFromPositionalContext(context, SUBTRACT);
		}
	}

	/**
	 * Implements the {@link Multiplier} interface.
	 * 
	 * @param context
	 *            input context for this operation
	 * @return an output service context
	 * @throws RemoteException
	 * @throws ContextException 
	 */
	public Context multiply(Context context)
			throws RemoteException, ContextException {
		if (context instanceof ArrayContext) {
			return calculateFromArrayContext(context, MULTIPLY);
		} else {
			return calculateFromPositionalContext(context, MULTIPLY);
		}
	}

	/**
	 * Implements the {@link Divider} interface.
	 * 
	 * @param context
	 *            input context for this operation
	 * @return an output service context
	 * @throws ContextException 
	 * @throws RemoteException
	 */
	public Context divide(Context context) throws RemoteException, ContextException {
		if (context instanceof ArrayContext) {
			return calculateFromArrayContext(context, DIVIDE);
		} else {
			return calculateFromPositionalContext(context, DIVIDE);
		}
	}

	public Context average(Context context) throws RemoteException, ContextException {
		if (context instanceof ArrayContext) {
			return calculateFromArrayContext(context, AVERAGE);
		} else {
			return calculateFromPositionalContext(context, AVERAGE);
		}
	}
	
	public Context calculate(Context context) throws RemoteException, ContextException {
			return calculateExpression(context);
	}
	
	/**
	 * Calculates the result of arithmetic operation specified by a selector
	 * (add, subtract, multiply, or divide) from the instance of ArrayContext.
	 * 
	 * @param context
	 *            service context
	 * @param selector
	 *            a name of arithmetic operation
	 * @return
	 * @throws RemoteException
	 * @throws ContextException 
	 * @throws ContextException
	 * @throws UnknownHostException
	 */
	private Context calculateFromArrayContext(Context context, String selector)
			throws RemoteException, ContextException {
		ArrayContext cxt = (ArrayContext) context;
		try {
			// get sorted list of input values
			List<Double> inputs = (List<Double>)cxt.getInValues();
//			logger.info("inputs: \n" + inputs);
			List<String> outpaths = cxt.getOutPaths();
//			logger.info("outpaths: \n" + outpaths);

			double result = 0;
			if (selector.equals(ADD)) {
				result = 0;
				for (Double value : inputs)
					result += value;
			} else if (selector.equals(SUBTRACT)) {
				result = inputs.get(0);
				for (int i = 1; i < inputs.size(); i++)
					result -= inputs.get(i);
			} else if (selector.equals(MULTIPLY)) {
				result = inputs.get(0);
				for (int i = 1; i < inputs.size(); i++)
					result *= inputs.get(i);
			} else if (selector.equals(DIVIDE)) {
				result = inputs.get(0);
				for (int i = 1; i < inputs.size(); i++)
					result /= inputs.get(i);
			} else if (selector.equals(AVERAGE)) {
				result = inputs.get(0);
				for (int i = 1; i < inputs.size(); i++)
					result += inputs.get(i);
				
				result = result / inputs.size();
			}

			logger.info(selector + " result: \n" + result);

			String outputMessage = "calculated by " + getHostname();
			// set return value
			if (((ServiceContext)context).getReturnPath() != null) {
				context.setReturnValue(result);
			}
			//other ways to indicate the output value
			else if (outpaths.size() == 1) {
				// put the result in the existing output path
				cxt.putValue(outpaths.get(0), result);
				cxt.putValue(path(outpaths.get(0), ArrayContext.DESCRIPTION), outputMessage);
			} else {
				// put the result for a new output path
				logger.info("max index; " + cxt.getMaxIndex());
				int oi = cxt.getMaxIndex() + 1;
				cxt.ov(oi, result);
				cxt.ovd(oi, outputMessage);
			}

		} catch (Exception ex) {
			// ContextException, UnknownHostException
			context.reportException(ex);
			throw new ContextException(selector + " calculate exception", ex);
		}
		return (Context) context;
	}

	/**
	 * Calculates the result of arithmetic operation specified by a selector
	 * (add, subtract, multiply, or divide) from the instance of ServiceContext.
	 * 
	 * @param cxt
	 * @param selector
	 *            a name of arithmetic operation
	 * @return
	 * @throws RemoteException
	 * @throws ContextException
	 * @throws UnknownHostException
	 */
	private Context calculateFromPositionalContext(Context cxt, String selector)
			throws RemoteException, ContextException {
		PositionalContext context = (PositionalContext) cxt;
		try {
			logger.info("arithmometer context: " + context);
			//logger.info("selector: " + ((ServiceContext)context).getCurrentSelector());
			// get sorted list of input values
			List<Double> inputs = (List<Double>)Contexts.getNamedInValues(context);
			if (inputs == null || inputs.size() == 0) {
				inputs = (List<Double>)Contexts.getPrefixedInValues(context);
//				logger.info("prefixed inputs: \n" + inputs);
			}
			//logger.info("named inputs: \n" + inputs);
			if (inputs == null || inputs.size() == 0)
				inputs = (List<Double>)context.getInValues();
			logger.info("inputs: \n" + inputs);
			logger.info("inputs paths: \n" + Contexts.getInPaths(context));
			List<String> outpaths = context.getOutPaths();
			//logger.info("outpaths: \n" + outpaths);

			double result = 0.0;
			if (selector.equals(ADD)) {				
					result = (Double)revalue(inputs.get(0));
				for (int i = 1; i < inputs.size(); i++)
					result += (Double)revalue(inputs.get(i));
			} else if (selector.equals(SUBTRACT)) {
				ReturnPath<?> rp = ((ServiceContext<?>) context).getReturnPath();
				if (rp != null && rp.inPaths != null && rp.inPaths.length > 0) {
					result = (Double) revalue(cxt.getValue(rp.inPaths[0]));
					result -= (Double) revalue(cxt.getValue(rp.inPaths[1]));
				} else {
					if (inputs.size() > 2 || inputs.size() < 2) {
						throw new ContextException("two arguments needed for subtraction");
					}
					result = (Double) revalue(context.getInValueAt(1));
					result -= (Double) revalue(context.getInValueAt(2));
				}
			} else if (selector.equals(MULTIPLY)) {
				System.out.println("ZZZZZZZZZ context: " + context);
				System.out.println("ZZZZZZZ inputs: " + inputs);

				result = (Double)revalue(inputs.get(0));
				for (int i = 1; i < inputs.size(); i++)
					result *= (Double)revalue(inputs.get(i));
			} else if (selector.equals(DIVIDE)) {
				if (inputs.size() > 2 || inputs.size() < 2)
					throw new ContextException("two arguments needed for division");
				result = (Double)revalue(context.getInValueAt(1));
				result /= (Double)revalue(context.getInValueAt(2));
			} else if (selector.equals(AVERAGE)) {
				if (inputs.size() == 0) {
					inputs = (List<Double>) Contexts.getNamedOutValues(context);
				}
				result = (Double) revalue(inputs.get(0));
				for (int i = 1; i < inputs.size(); i++)
					result += (Double) revalue(inputs.get(i));

				result = result / inputs.size();
			}
			logger.info(selector + " result: \n" + result);

			String outputMessage = "calculated by " + getHostname();
			if (((ServiceContext)context).getReturnPath() != null) {
				String outpath = ((ServiceContext)context).getReturnPath().path;
				if (outpath.indexOf("${name}") >= 0) {
					String out = outpath.replace("${name}", 
							((ServiceContext)context).getExertion().getName());
					((ServiceContext)context).getReturnPath().path = out;
				}
				((ServiceContext)context).setReturnValue(result);
			}
			else if (outpaths.size() == 1) {
				// put the result in the existing output path
				String outpath = outpaths.get(0);
				if (outpath.indexOf("${name}") >= 0) {
					if (outpath.indexOf("${name}") >= 0) {
						outpath = outpath.replace("${name}", 
							((ServiceContext)context).getExertion().getName());
					}
				}
				cxt.putValue(outpath, result);
				cxt.putValue(path(outpath, ArrayContext.DESCRIPTION), outputMessage);
			} else {
				cxt.putValue(RESULT_PATH, result);
				cxt.putValue(path(RESULT_PATH, ArrayContext.DESCRIPTION), outputMessage);
			}
		} catch (Exception ex) {
			// ContextException, UnknownHostException
			ex.printStackTrace();
			context.reportException(ex);
			throw new ContextException(selector + " calculate exception", ex);
		}
		return (Context) context;
	}
	
	private Context calculateExpression(Context context)
			throws RemoteException, ContextException {
		String expr = (String) context.getValue("arithmetic/expression");
		List<String> outpaths = ((ServiceContext) context).getOutPaths();
		GroovyShell gsh = new GroovyShell();
		Object result = gsh.evaluate(expr);
		String outputMessage;
		try {
			outputMessage = "calculated by " + getHostname();
			if (((ServiceContext) context).getReturnPath() != null) {
				String outpath = ((ServiceContext) context).getReturnPath().path;
				if (outpath.indexOf("${name}") >= 0) {
					String out = outpath.replace("${name}",
							((ServiceContext) context).getExertion().getName());
					((ServiceContext) context).getReturnPath().path = out;
				}
				((ServiceContext) context).setReturnValue(result);
			} else if (outpaths.size() == 1) {
				// put the result in the existing output path
				String outpath = outpaths.get(0);
				if (outpath.indexOf("${name}") >= 0) {
					if (outpath.indexOf("${name}") >= 0) {
						outpath = outpath.replace("${name}",
								((ServiceContext) context).getExertion()
										.getName());
					}
				}
				context.putValue(outpath, result);
				context.putValue(path(outpath, ArrayContext.DESCRIPTION),
						outputMessage);
			} else {
				context.putValue(RESULT_PATH, result);
				context.putValue(path(RESULT_PATH, ArrayContext.DESCRIPTION),
						outputMessage);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			context.reportException(ex);
			throw new ContextException("calculate expression", ex);
		}
		return context;
	}
	
	/**
	 * Returns name of the local host.
	 * 
	 * @return local host name
	 * @throws UnknownHostException
	 */
	private String getHostname() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostName();
	}

}
