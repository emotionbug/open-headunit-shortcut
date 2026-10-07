"""Regenerate pre-Android-8 launcher PNGs (requires Pillow; not needed to build)."""
from pathlib import Path
from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parents[1]
for density, size in [('mdpi', 48), ('hdpi', 72), ('xhdpi', 96), ('xxhdpi', 144), ('xxxhdpi', 192)]:
    scale = 8
    image = Image.new('RGBA', (size * scale, size * scale), (0, 0, 0, 0))
    draw = ImageDraw.Draw(image)
    unit = size * scale / 108
    def box(coords):
        return tuple(round(x * unit) for x in coords)
    draw.rounded_rectangle(box((2, 2, 106, 106)), radius=22 * unit, fill='#102A43')
    draw.rectangle(box((22, 25, 86, 71)), fill='white')
    draw.rectangle(box((28, 31, 80, 65)), fill='#102A43')
    draw.rectangle(box((40, 77, 68, 82)), fill='white')
    draw.polygon([(round(x * unit), round(y * unit)) for x, y in
                  [(38, 45), (58, 45), (58, 36), (75, 48), (58, 60), (58, 51), (38, 51)]], fill='#43D9BD')
    dest = ROOT / f'app/src/main/res/mipmap-{density}/ic_launcher.png'
    dest.parent.mkdir(parents=True, exist_ok=True)
    image.resize((size, size), Image.Resampling.LANCZOS).save(dest)
