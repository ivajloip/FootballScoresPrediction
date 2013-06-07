package bg.uni_sofia.fmi.football_predictor.core;

import java.io.Serializable;

import org.hibernate.Criteria;

public abstract class DataBaseObject implements Serializable {

	public abstract String makeSelectStatement();

	public abstract Criteria createCriteria(Criteria criteria);
}
