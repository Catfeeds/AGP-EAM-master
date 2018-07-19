package com.hsk.hxqh.agp_eam.model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzw on 2018/7/13.
 */

public class SUBJECT {
    public static Map<String,String> stringStringMap = new Hashtable<>();
    public static List<String> list = new ArrayList<>();
    static {
        stringStringMap.put("Служба безопасности / Security Service","SS");
        stringStringMap.put("Служба ЭВС/ Power/Water Supply Service","PWS");
        stringStringMap.put("Начальника (КЗ)/Head  (KZ)","DC");
        stringStringMap.put("химик/Chemistry Service","CH");
        stringStringMap.put("Главный инженер/Chief Engineer","CE");
        stringStringMap.put("Служба ЭХЗ/ Cathodic Protection Service","CP");
        stringStringMap.put("Служба АСУТП / Automated Process Control Service","AP");
        stringStringMap.put("Линейно-аварийная служба/Linear Emergency Service","LP");
        stringStringMap.put("Кладовщик//Warehouse Keeper","WK");
        stringStringMap.put("Транспортная служба / Transportation Service","TP");
        stringStringMap.put("Компрессорная служба / Compressor Service","CS");
        stringStringMap.put("Служба технического обслуживания и ремонта/Technical maintenance and Repair Service","TM");
        list.add("Служба безопасности / Security Service");
        list.add("Служба ЭВС/ Power/Water Supply Service");
        list.add("Начальника (КЗ)/Head  (KZ)");
        list.add("химик/Chemistry Service");
        list.add("Главный инженер/Chief Engineer");
        list.add("Служба ЭХЗ/ Cathodic Protection Service");
        list.add("Служба АСУТП / Automated Process Control Service");
        list.add("Линейно-аварийная служба/Linear Emergency Service");
        list.add("Кладовщик//Warehouse Keeper");
        list.add("Транспортная служба / Transportation Service");
        list.add("Компрессорная служба / Compressor Service");
        list.add("Служба технического обслуживания и ремонта/Technical maintenance and Repair Service");
    }
    public static String getKeyByValue(String value) {
        String keys = "";
        Iterator it = stringStringMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String obj = (String) entry.getValue();
            if (obj != null && obj.equals(value)) {
                keys = (String) entry.getKey();
            }
        }
        return keys;
    }

    }
