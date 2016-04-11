package com.example.deean.medix.doktorovo;

import com.example.deean.medix.doktorovo.konsturktor_i_baza.Doktor;

/**
 * Created by Deean on 10.2.2016..
 */
public interface GetUserCallback {
    public abstract void done(Doktor returnedDoktor);
}
