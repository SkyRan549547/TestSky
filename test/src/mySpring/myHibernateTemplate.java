package mySpring;

import java.util.List;

public class myHibernateTemplate {

	public List find(String queryString, Object[] values)
			throws DataAccessException {
		return (List) execute(new HibernateCallback(queryString, values) {
			private final String val$queryString;
			private final Object[] val$values;

			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createQuery(this.val$queryString);
				HibernateTemplate.this.prepareQuery(queryObject);
				if (this.val$values != null) {
					for (int i = 0; i < this.val$values.length; i++) {
						queryObject.setParameter(i, this.val$values[i]);
					}
				}
				return queryObject.list();
			}
		}, true);
	}

}
