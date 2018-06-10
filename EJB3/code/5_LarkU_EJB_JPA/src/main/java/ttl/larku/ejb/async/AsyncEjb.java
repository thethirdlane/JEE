package ttl.larku.ejb.async;

import java.util.concurrent.Future;

import javax.ejb.Asynchronous;

public interface AsyncEjb {

	public abstract Future<Double> longCalculation(int i);

}