package com.example.practicaJMSjms.DataBase;

import com.example.practicaJMSjms.Entidades.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorServiceImpl implements SensorService {
    @Autowired
    private SensorRepository sensorRepository;

    public boolean saveData(Info info) {
        Sensor sensor = new Sensor(info.getFechaGeneracion(), info.getTemperatura(), info.getHumedad());
        sensorRepository.save(sensor);
        return true;
    }
}
