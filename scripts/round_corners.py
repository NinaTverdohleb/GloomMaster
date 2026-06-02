# round_corners.py
import re
from PIL import Image, ImageDraw
from pathlib import Path

INPUT_DIR = Path("input")
OUTPUT_DIR = Path("output")
RADIUS = 40

NAME_RE = re.compile(r"^[A-Za-z]+-(\d+)[A-Za-z]*(?:-.*)?$")

OUTPUT_DIR.mkdir(exist_ok=True)

for path in INPUT_DIR.glob("*.png"):
    match = NAME_RE.match(path.stem)
    if not match:
        print(f"✗ {path.name} — skipped (unexpected name)")
        continue

    number = int(match.group(1))
    output_name = f"item_{number}.webp"

    img = Image.open(path).convert("RGBA")
    w, h = img.size

    mask = Image.new("L", (w, h), 0)
    draw = ImageDraw.Draw(mask)
    draw.rounded_rectangle([(0, 0), (w - 1, h - 1)], radius=RADIUS, fill=255)
    img.putalpha(mask)

    img.save(OUTPUT_DIR / output_name, format="WEBP", lossless=True)
    print(f"✓ {path.name} → {output_name}")