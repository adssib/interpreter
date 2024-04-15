import interfaces.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import enums.TokenType;

public class lexer {

    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();

    
    public static Token token(String value , TokenType type){
        return new TokenImpl(value, type);
    }
    
    public static ArrayList<Token> tokenize(String sourceCode) {
        ArrayList<Token> tokens = new ArrayList<Token>() ; 
        String[] src = sourceCode.split("")  ; 
        while (src.length > 0) {
            if (src[0].equals("(")) {
                tokens.add(token(src[0], TokenType.OPEN_PAREN));
                src = shift(src);
            } else if (src[0].equals(")")) {
                tokens.add(token(src[0], TokenType.CLOSE_PAREN));
                src = shift(src);
            } else if (src[0].equals("+") || src[0].equals("-") || src[0].equals("*") || src[0].equals("/")) {
                tokens.add(token(src[0], TokenType.BINARY_OPERATOR));
                src = shift(src);
            } else if (src[0].equals("=")) {
                tokens.add(token(src[0], TokenType.EQUALS));
                src = shift(src);
            } else {
                if (isInt(src[0])) {
                    StringBuilder num = new StringBuilder();
                    while (src.length > 0 && isInt(src[0])) {
                        num.append(src[0]);
                        src = shift(src);
                    }
                    tokens.add(token(num.toString(), TokenType.NUMBER));
                } else if (isAlpha(src[0])) {
                    StringBuilder ident = new StringBuilder();
                    while (src.length > 0 && isAlpha(src[0])) {
                        ident.append(src[0]);
                        src = shift(src);
                    }
                    TokenType reserved = KEYWORDS.get(ident.toString());
                    if (reserved != null) {
                        tokens.add(token(ident.toString(), reserved));
                    } else {
                        tokens.add(token(ident.toString(), TokenType.IDENTIFIER));
                    }
                } else if (isSkippable(src[0])) {
                    src = shift(src);
                } else {
                    System.err.println("Unrecognized character found in source: " + (int) src[0].charAt(0) + " " + src[0]);
                    System.exit(1);
                }
            }
        }
        return tokens;
    }

    public static String[] shift(String[] src) {
        String[] shiftedArray = new String[src.length - 1];
        for (int i = 1; i < src.length; i++) {
            shiftedArray[i - 1] = src[i];
        }  
        return shiftedArray;
    }
    private static class TokenImpl implements Token {
        private final String value;
        private final TokenType type;
    
        public TokenImpl(String value, TokenType type) {
            this.value = value;
            this.type = type;
        }
    
        @Override
        public String getValue() {
            return value;
        }
    
        @Override
        public TokenType getType() {
            return type;
        }
    }
    
    private static boolean isAlpha(String src) {
        return src.toUpperCase().equals(src.toLowerCase());
    }

    private static boolean isInt(String src) {
        char c = src.charAt(0);
        return c >= '0' && c <= '9';
    }

    private static boolean isSkippable(String str) {
        return str.equals(" ") || str.equals("\n") || str.equals("\t");
    }
}

