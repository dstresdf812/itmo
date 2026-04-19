import hisscl
import tomlkit
file = "input.hcl"
data = hisscl.load_file(file)

print(data)

data_new = tomlkit.dumps(data,sort_keys=True)

print(data_new)
with open("result.toml", "w", encoding="utf-8") as f:
    f.write(data_new)