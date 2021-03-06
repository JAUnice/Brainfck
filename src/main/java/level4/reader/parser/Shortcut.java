package level4.reader.parser;

import level4.instructions.Executable;
import level4.exceptions.ArgumentException;
import level4.utils.Parametrized;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Shortcut {
    private Map<String, Parametrized> executable;

    public Shortcut() {
        executable = new HashMap<>();
    }

    /**
     * add executable to map
     *
     * @param name name of the procedure
     * @param proc procedure to be added
     */
    public void addProcedure(String name, Parametrized proc) {
        executable.put(name, proc);
    }

    /**
     * return the procedure called by name
     *
     * @param name call of the procedure (i.e. procedureExample[5,9]
     * @return executable
     */
    public Executable getExecutable(String name) {
        Pattern findDeclaration = Pattern.compile("(.*)\\[(([0-9]|,)*)]");
        Matcher m = findDeclaration.matcher(name);
        if (m.matches() && executable.containsKey(m.group(1))) {
            return executable.get(m.group(1)).getFunction(toShortArray(m.group(2).split(",")));
        }
        return null;
    }

    /**
     * convert a String array into short array
     * private because the array have to be parsable to short
     * here it is ensure by the regexp
     * @param array
     * @return
     */
    private short[] toShortArray(String[] array) {
        short[] param = new short[array.length];
        if (array.length == 1 && array[0].equals("")) return param = new short[0];
        for (int i = 0; i < array.length; i++) {
            try {
                int num = Integer.parseInt(array[i]);
                param[i] = (short) num;
            } catch (Exception e) {
                throw new ArgumentException("wrong-function-arg");
            }
        }
        return param;
    }

}
