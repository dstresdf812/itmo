from hcl import tokenizer,to_toml,to_xml
from time import perf_counter


file = "hcl/input.hcl"

start = perf_counter()
for i in range(100):
    data = to_toml.save_toml(tokenizer.parse_file(file))
end = perf_counter()
print(f"{end - start:.5f} HCL -> TOML")

start = perf_counter()
for i in range(100):
    data = to_xml.save_xml(tokenizer.parse_file(file))
end = perf_counter()
print(f"{end - start:.5f} HCL -> XML")

import dicttoxml
import hisscl
import tomlkit

start = perf_counter()
for i in range(100):
    data = hisscl.load_file(file)
    with open("output1.toml", "w", encoding="utf-8") as f:
        f.write(tomlkit.dumps(data))
end = perf_counter()
print(f"{end - start:.5f} LIBRARY HCL -> XML")

start = perf_counter()
for i in range(100):
    data = hisscl.load_file(file)
    with open("output1.xml", "w", encoding="utf-8") as f:
        f.write(dicttoxml.dicttoxml(data).decode())
end = perf_counter()
print(f"{end - start:.5f} LIBRARY HCL -> TOML")