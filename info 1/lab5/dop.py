import pandas
data = open("final_Version.csv").readlines()
i = 1
new_data = []
for line in data:
    if i >= 4:
        cur = line.split(";")
        cur = [str(x).replace("\n","") for x in cur]
        cur.pop(5)
        new_data.append(cur)
    i+=1

df = pandas.DataFrame(new_data)
print(df)
print(df.columns.tolist())

styled = df.style \
    .set_table_styles([
    {'selector': 'th.col0, td.col0',
     'props': [('background-color', 'pink'), ]},
    {'selector': 'th.col1, td.col1',
     'props': [('background-color','red'),]},
    {'selector': 'th.col2, td.col2',
     'props': [('background-color','purple'),]},
    {'selector': 'th.col4, td.col4, th.col3, td.col3',
     'props': [('background-color', 'cyan'), ]},
    {'selector': 'th.col5,td.col5, th.col6,td.col6, th.col7,td.col7, th.col8,td.col8, th.col9,td.col9, th.col10,td.col10, th.col11,td.col11, th.col12,td.col12, th.col13,td.col13, th.col14,td.col14, th.col15,td.col15, th.col16,td.col16, th.col17,td.col17, th.col18,td.col18, th.col19,td.col19, th.col20,td.col20, th.col21,td.col21, th.col22,td.col22, th.col23,td.col23',
     'props': [('background-color', 'green'), ]},

], overwrite=False)

styled.to_html('table_with_borders.html')
styled.to_html('final_Version.html')

import pandas as pd

s = ""
for i in range(5,24):
    s += f"td.col{i},"

styled = df.style \
    .hide(axis="index") \
    .hide(axis="columns") \
    .set_table_styles([
        {'selector': '', 'props': [('border-collapse', 'collapse')]},
        {'selector': 'td.col0', 'props': [('background-color', '#34495e')]},
        {'selector': 'td.col1', 'props': [('background-color', '#463680')]},
        {'selector': 'td.col2', 'props': [('background-color', '#871f80')]},
        {'selector': 'td.col3,td.col4', 'props': [('background-color', '#871f3c')]},
        {'selector': 'td.col5,td.col6,td.col7,td.col8,td.col9,td.col10,td.col11,td.col12\
        ,td.col13,td.col14,td.col15,td.col16,td.col17,td.col18,td.col19,td.col20,td.col21\
        ,td.col22,td.col23', 'props': [('background-color', '#1d612a')]},
        {'selector' : '', 'props': [('border', '3px solid black')]},
    ])



styled.to_html('lab5_result.html', doctype_html=True)