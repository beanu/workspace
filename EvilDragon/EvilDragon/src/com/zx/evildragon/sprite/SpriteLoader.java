package com.zx.evildragon.sprite;

import info.u250.c2d.engine.Engine;
import info.u250.spriter.file.FileLoader;
import info.u250.spriter.file.Reference;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class SpriteLoader extends FileLoader<Sprite> {

	private final TextureAtlas atlas;

	public SpriteLoader() {
		atlas = Engine.resource("cat");
	}

	@Override
	public void load(Reference ref, String path) {
		// Pixmap pix = new Pixmap(Gdx.files.local(path));
		// Pixmap pix1 = new Pixmap(roundToPowerOfTwo(pix.getWidth()),
		// roundToPowerOfTwo(pix.getHeight()), Pixmap.Format.RGBA8888);
		// pix1.drawPixmap(pix, 0, 0);
		// Texture texture = new Texture(pix1);
		// texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		// TextureRegion region = new TextureRegion(texture, 0, 0,
		// pix.getWidth(), pix.getHeight());

		String[] temp = path.split("/");
		String fileName = temp[temp.length - 1];

		String name = fileName.substring(0, fileName.indexOf("."));
		Sprite sprite = new Sprite(atlas.findRegion(name));
		files.put(ref, sprite);
	}

}
