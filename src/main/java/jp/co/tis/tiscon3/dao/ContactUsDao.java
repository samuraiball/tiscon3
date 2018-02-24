package jp.co.tis.tiscon3.dao;

import jp.co.tis.tiscon3.configuration.DatabaseConfiguration;
import jp.co.tis.tiscon3.entity.ContactUs;
import org.seasar.doma.Dao;
import org.seasar.doma.Insert;

/**
 *
 * @author hirooka
 */
@Dao(config = DatabaseConfiguration.class)
public interface ContactUsDao {

    @Insert
    int insert(ContactUs contactUs);

}
