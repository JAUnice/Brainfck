package level4.reader.parser;

import level4.reader.InstructionReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum parser implements Parse {
    COMMENT {
        @Override
        public void parse(InstructionReader res) {
            res.replaceText(" *#(.*)", "");
        }
    },
    SPACE {
        @Override
        public void parse(InstructionReader res) {
            res.replaceText("^ *", "");
            res.replaceText("\n *", "\n");
            res.replaceText("\t","");
            res.replaceText(" *\n","\n");
        }
    },
    MACRO {
        @Override
        public void parse(InstructionReader res) {
            Pattern findMacroDef = Pattern.compile(" (.*)\\n([\\S\\s]*\\n)}[\\S\\s]*"); //pattern to get the utils and his definition
            String[] macro = res.getText().split("\\{"); //splitting at '{' to get only one utils definition per segment
            for (int i = 0; i < macro.length; i++) { //each segment is parsed
                Matcher m = findMacroDef.matcher(macro[i]);
                if (m.matches()) {
                    parser.MACRO.replaceMacro(res, m.group(1), m.group(2)); //to treat case of multiple utils call
                    macro = res.getText().split("\\{"); //utils is redefinied to "refresh" his content
                }

            }
            res.replaceText("\\{(.|\\n)*}", ""); //erase all the utils definition
        }
    },
    PROC {
        @Override
        public void parse(InstructionReader res) {
            Pattern findProcDef = Pattern.compile("Proc (.*)\\n([\\S\\s]*\\n)\\)[\\S\\s]*");
            String[] proc = res.getText().split("\\(");
            for (int i = 0; i < proc.length; i++) { //each segment is parsed
                Matcher m = findProcDef.matcher(proc[i]);
                if (m.matches()) {
                    res.addProc(m.group(1), m.group(2));
                }

            }
        }
    },
    FUNC {
        @Override
        public void parse(InstructionReader res) {
            Pattern findFuncDef = Pattern.compile("Func (.*)\\n([\\S\\s]*)\\n\\)[\\S\\s]*");
            String[] proc = res.getText().split("\\(");
            for (int i = 0; i < proc.length; i++) { //each segment is parsed
                Matcher m = findFuncDef.matcher(proc[i]);
                if (m.matches()) {
                    res.addFunc(m.group(1), m.group(2));
                }

            }
            res.replaceText("\\(Proc (.|\\n)*\\n\\)", "");
            res.replaceText("\\(Func (.|\\n)*\\n\\)", "");
        }
    };

    private static String buildString(String content, int repetition) {
        StringBuilder res = new StringBuilder(content);
        for (int i = 1; i < repetition; i++) {
            res.append(content);
        }
        return res.toString();
    }

    private void replaceMacro(InstructionReader res, String name, String content) {
        Pattern findMacroUseWithParameter = Pattern.compile("(.|\\n)*" + name + "%([0-9]*)(.|\\n)*");
        Matcher m = findMacroUseWithParameter.matcher(res.getText());
        while (m.matches()) {
            res.replaceText(name + "%" + m.group(2), buildString(content, Integer.parseInt(m.group(2))));
            m = findMacroUseWithParameter.matcher(res.getText());
        }
        res.replaceText(name, content);

    }
}
