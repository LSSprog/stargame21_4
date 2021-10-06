package com.star.app.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.star.app.game.helpers.Poolable;
import com.star.app.screen.ScreenManager;
import com.star.app.screen.utils.Assets;

public class Bonus implements Poolable {
    private TextureRegion texture;
    private Vector2 position;
    private Vector2 velocity;
    private int amount;
    private Circle hitArea;
    private boolean active;
    private GameController gc;

    private final float BASE_SIZE = 64.0f;
    private final float BASE_RADIUS = BASE_SIZE / 2;

    public Bonus(GameController gc) {
        this.texture = Assets.getInstance().getAtlas().findRegion("bullet");
        this.position = new Vector2(0, 0);
        this.velocity = new Vector2(0, 0);
        this.amount = 10;
        this.hitArea = new Circle(0, 0, 0);
        this.active = false;
        this.gc = gc;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public int getAmount() {
        return amount;
    }

    public Circle getHitArea() {
        return hitArea;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - 16, position.y - 16, 16, 16,
                32, 32, 2, 2, 0.0f);
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate(float x, float y, float vx, float vy) {
        this.position.set(x, y);
        this.velocity.set(vx, vy);
        this.hitArea.setPosition(position);
        this.active = true;
        this.hitArea.setRadius(BASE_RADIUS);
    }

    public boolean take() {
        deactivate();
        gc.getHero().getCurrentWeapon().setCurBullets(gc.getHero().getCurrentWeapon().getCurBullets() + amount);
        return true;
    }

    public void update(float dt) {
        position.mulAdd(velocity, dt);
        hitArea.setPosition(position);

        if ((position.x < -100) || (position.x > ScreenManager.SCREEN_WIDTH + 100) ||
                (position.y < -100) || (position.y > ScreenManager.SCREEN_HEIGHT + 100)) {
            deactivate();
        }
    }

}
