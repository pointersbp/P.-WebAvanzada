package com.example.practicaJMSjms.DataBase;

import com.example.practicaJMSjms.Entidades.Info;
import org.springframework.stereotype.Service;

public interface SensorService {
    public boolean saveData(Info info);
}
