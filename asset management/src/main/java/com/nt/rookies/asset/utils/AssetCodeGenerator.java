package com.nt.rookies.asset.utils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import com.nt.rookies.asset.entities.AssetEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;


@Getter
@Setter
public class AssetCodeGenerator implements IdentifierGenerator {

	private Integer maxId = 0;

	@Override
	public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
		IdentifierGenerator.super.configure(type, params, serviceRegistry);
	}

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj)
			throws HibernateException {

		Connection connection = session.connection();
		AssetEntity asset = (AssetEntity) obj;
		try {
			PreparedStatement ps = connection.prepareStatement("select * from asset a where a.code like '"
					+ asset.getCategory().getPrefix() + "%' order by a.code desc limit 1");
			ResultSet rs = ps.executeQuery();

			boolean flag = false;

			if (rs.next()) {
				String numberPart = rs.getString("code")
						.substring(String.valueOf(asset.getCategory().getPrefix()).length());
				try {
					Integer numPart = Integer.parseInt(numberPart);
					this.setMaxId(numPart);
					flag = true;
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (!flag) {
				this.setMaxId(1);
			}
			else {
				this.setMaxId((this.getMaxId() + 1));
			}
			connection.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return asset.getCategory().getCategoryId() + String.format("%06d", this.getMaxId());
	}
}
