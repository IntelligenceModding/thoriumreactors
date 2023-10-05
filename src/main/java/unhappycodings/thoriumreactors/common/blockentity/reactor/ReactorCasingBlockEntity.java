package unhappycodings.thoriumreactors.common.blockentity.reactor;

import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import unhappycodings.thoriumreactors.common.ccperipherals.CCReactorPeripheral;
import unhappycodings.thoriumreactors.common.blockentity.reactor.base.ReactorFrameBlockEntity;
import unhappycodings.thoriumreactors.common.registration.ModBlockEntities;

import static dan200.computercraft.shared.Capabilities.CAPABILITY_PERIPHERAL;

public class ReactorCasingBlockEntity extends ReactorFrameBlockEntity {

    public ReactorCasingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.REACTOR_CASING.get(), pPos, pBlockState);
    }

    protected CCReactorPeripheral peripheral = new CCReactorPeripheral(this);
    private LazyOptional<IPeripheral> peripheralCap;

    /**
     * When a computer modem tries to wrap our block, the modem will call getCapability to receive our peripheral.
     * Then we just simply return a {@link LazyOptional} with our Peripheral
     */
    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction direction) {
        if (cap == CAPABILITY_PERIPHERAL) {
            if (peripheralCap == null) {
                peripheralCap = LazyOptional.of(() -> peripheral);
            }
            return peripheralCap.cast();
        }
        return super.getCapability(cap, direction);
    }


}
