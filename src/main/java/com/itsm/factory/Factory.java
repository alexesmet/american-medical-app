package com.itsm.factory;

import java.util.Scanner;

public interface Factory<T> {
    T create(long id);
}
