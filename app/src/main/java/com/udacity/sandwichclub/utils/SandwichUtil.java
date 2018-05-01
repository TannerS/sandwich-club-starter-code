package com.udacity.sandwichclub.utils;

import java.util.List;

public class SandwichUtil {
    /**
     * Turn list of strings into comma separated string
     * @param   items       list of strings
     * @return              comma separated string
     */
    public static String listToString(List<String> items)
    {
        StringBuilder mBuilder = new StringBuilder();

        for(int i = 0; i < items.size(); i++)
        {
            if(i == items.size() - 1) {
                mBuilder.append(items.get(i));
            }
            else
            {
                mBuilder.append(items.get(i));
                mBuilder.append(", ");
            }
        }

        return mBuilder.toString();
    }
}
