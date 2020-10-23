package br.com.dbc.data.analysis.factories;

public interface AbstractFactory<T> {
	T createInstance(String line);
}
