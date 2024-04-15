package interfaces;

import enums.TokenType;

public interface Token {
    String getValue(); // contains the raw value as seen inside the source code.
    TokenType getType(); // tagged structure.
}
