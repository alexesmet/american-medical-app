package com.itsm.frontend.storage.interf;

import com.itsm.common.entity.User;
import com.itsm.frontend.storage.Storage;

public interface InterfaceUserStorage extends Storage<User> {
    User getByName(String name);
    User getByEmail(String email);

}
