import re

with open('./app/src/main/kotlin/com/rumpilstilstkin/gloomhavenhelper/bd/filler/GoodsFiller.kt', 'r') as f:
    content = f.read()

# Find all GoodBd blocks that don't have image field
pattern = r'(GoodBd\(\s*\n\s*number = (\d+),\s*\n\s*name = "[^"]+",\s*\n\s*type = GoodType\.\w+\.name,)(\s*\n\s*cost)'

def add_image(match):
    before = match.group(1)
    number = match.group(2)
    after = match.group(3)

    return f'{before}\n                image = "item_{number}.webp",{after}'

result = re.sub(pattern, add_image, content)

with open('./app/src/main/kotlin/com/rumpilstilstkin/gloomhavenhelper/bd/filler/GoodsFiller.kt', 'w') as f:
    f.write(result)

print("Done!")