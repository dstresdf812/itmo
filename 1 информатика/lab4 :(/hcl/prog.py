# SERIALIZE
from tokenizer import parse_file

# TOML
from to_toml import save_toml

# XML
from to_xml import save_xml

# MAIN PART
data = parse_file("input.hcl")
save_toml(data)
save_xml(data)

# VAR
print(501145%132)