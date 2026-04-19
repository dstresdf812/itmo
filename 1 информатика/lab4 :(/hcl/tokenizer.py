def get_tokens(text):
    tokens = []
    i = 0
    while i < len(text):
        symb = text[i]
        if symb == " " or symb == "\n":
            i += 1
        elif symb in '{}=':
            tokens.append(symb)
            i += 1
        elif symb == '"':
            i += 1
            s = ''
            while i < len(text) and text[i] != '"':
                s += text[i]
                i += 1
            i += 1
            tokens.append(('STRING', s))
        else:
            s = symb
            i += 1
            while i < len(text) and (text[i].isalnum() or text[i] in '_-()., '):
                s += text[i]
                i += 1
            tokens.append(('IDENT', s.strip()))
    return tokens

def parse_value(tokens, pos):
    tok = tokens[pos]
    if tok[0] == 'STRING':
        return tok[1], pos + 1
    elif tok[0] == 'IDENT':
        val = tok[1]
        try:
            val = int(val)
            return val, pos + 1
        except:
            if val.lower() == 'true':
                return True, pos + 1
            elif val.lower() == 'false':
                return False, pos + 1
            else:
                return val, pos + 1

def parse_assignment(tokens, pos):
    key = tokens[pos][1]
    pos += 1
    pos += 1
    value, pos = parse_value(tokens, pos)
    return key, value, pos

def parse_block(tokens, pos):
    key = tokens[pos][1]
    pos += 1
    pos += 1
    block = {}
    while pos < len(tokens) and tokens[pos] != '}':
        tok = tokens[pos]
        if tok[0] == 'IDENT':
            next_tok = tokens[pos + 1]
            if next_tok == '{':
                temp_key, val, pos = parse_block(tokens, pos)
                if temp_key in block:
                    if type(block[temp_key]) == list:
                        block[temp_key].append([val])
                    else:
                        block[temp_key] = [[block[temp_key]], [val]]
                else:
                    block[temp_key] = val
            elif next_tok == '=':
                temp_key, val, pos = parse_assignment(tokens, pos)
                block[temp_key] = val

    pos += 1
    return key, block, pos

def parse(tokens):
    pos = 0
    result = {}
    while pos < len(tokens):
        cur = tokens[pos]
        key, val, pos = parse_block(tokens, pos)
        result[key] = val
    return result

def parse_file(f):
    with open(f, "r", encoding="utf-8") as f:
        tokens = get_tokens(f.read())
    return parse(tokens)
