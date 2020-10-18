package br.com.dbc.data.analysis.factories;

public interface AbstractFactory {
	abstract Object createInstance(String[] lineSplitted);
}
