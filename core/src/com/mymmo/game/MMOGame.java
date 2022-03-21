package com.mymmo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import util.GameCamera;
import util.ResourceUtil;

public class MMOGame extends Game {

	GameScreen gameScreen;
	@Override
	public void create () {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}
}
