package mett.palemannie.spittingimage;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class SpittingImageConfig {

    public static final Common COMMON;
    public static final ModConfigSpec COMMON_SPEC;

    static {

        final Pair<Common, ModConfigSpec> commonSpec = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = commonSpec.getRight();
        COMMON = commonSpec.getLeft();
    }

    public static class Common {

        public final ModConfigSpec.DoubleValue spitDamage;
        public final ModConfigSpec.BooleanValue enable3dModel;

        Common(ModConfigSpec.Builder builder) {

            builder.comment("Common Settings").push("common");

            spitDamage = builder.comment("How much damage the spit deals (default: 1.0)").defineInRange("spitDamage", 1.0, 0.0, Float.MAX_VALUE);
            enable3dModel = builder.comment("Enables/Disables the player spit model").define("enable3dModel", true);

            builder.pop();
        }
    }
}