package unhappycodings.thoriumreactors.common.blockentity.reactor;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.Capabilities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.integration.CCReactorPeripheral;
import unhappycodings.thoriumreactors.common.blockentity.reactor.base.ReactorFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

public class ReactorCasingBlockEntity extends ReactorFrameBlockEntity {

    public ReactorCasingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REACTOR_CASING.get(), pPos, pBlockState);
    }

    protected CCReactorPeripheral peripheral;
    private LazyOptional<IPeripheral> peripheralCap;

    /**
     * When a computer modem tries to wrap our block, the modem will call getCapability to receive our peripheral.
     * Then we just simply return a {@link LazyOptional} with our Peripheral
     */
    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction direction) {
        if (ModList.get().isLoaded("computercraft") && cap == Capabilities.CAPABILITY_PERIPHERAL) {
            if (peripheral == null)
                this.peripheral = new CCReactorPeripheral(this);
            if (peripheralCap == null)
                peripheralCap = LazyOptional.of(() -> peripheral);

            return peripheralCap.cast();
        }
        return super.getCapability(cap, direction);
    }


}
