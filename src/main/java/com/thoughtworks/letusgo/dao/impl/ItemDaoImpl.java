package com.thoughtworks.letusgo.dao.impl;

import com.thoughtworks.letusgo.dao.ItemDao;
import com.thoughtworks.letusgo.entity.Category;
import com.thoughtworks.letusgo.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemDaoImpl implements ItemDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Item> getItems() {
        String sql = "select * from items,categories where items.category=categories.id";

        return namedParameterJdbcTemplate.query(sql, new ItemMapper());
    }

    @Override
    public Item getItemByBarcode(String barcode) {
        String sql = "select * from items,categories where barcode = :barcode and items.category=categories.id";

        Map<String, Object> namedParameters = new HashMap<String, Object>();
        namedParameters.put("barcode", barcode);

        return namedParameterJdbcTemplate.query(sql, namedParameters, new ItemMapper()).get(0);
    }

    private static final class ItemMapper implements RowMapper<Item> {
        @Override
        public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
            Item item = new Item(
                    rs.getInt("id"),
                    rs.getString("barcode"),
                    rs.getString("name"),
                    rs.getString("unit"),
                    rs.getDouble("price"),
                    new Category(
                            rs.getInt("category"),
                            rs.getString("categories.name")
                    )
            );
            return item;
        }
    }
}
