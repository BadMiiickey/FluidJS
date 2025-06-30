Examples (all of your code should be written in startup_scripts)

FluidEvents.register(event => {

    //the most literal methods that you can achieve your own ideas about fluid interact with fluid, block, and even entity!
    event.create(
        'kubejs:test_fluid',
        (level, currentPos, relativePos, currentState) => {
            if (level.getBlock(relativePos).id == 'minecraft:water') return true
            return false
        },
        (level, currentPos, relativePos, currentState) => {

            let sourceBlockState = Blocks.OBSIDIAN.defaultBlockState()
            let flowingBlockState = Blocks.COBBLESTONE.defaultBlockState()
            let newState = currentState.source ? sourceBlockState : flowingBlockState

            level.setBlockAndUpdate(currentPos, newState)
            level.levelEvent(1501, currentPos, 0)
        }
    )

    //pop items you defined on the fluids interact pos and remove arg0 fluid.
    event.createForItem('kubejs:test_fluid_1', 'minecraft:water', 'minecraft:bucket')

    //set particular block you defined on the fluids interact pos. arg2 is the block that turn to when interact with the source arg0, while the arg3 is the block that turn to when interact with the flowing arg0.
    event.createForBlock('kubejs:test_fluid_1', 'minecraft:water', 'minecraft:obsidian', 'minecraft:cobblestone')

    //set particular fluid you defined on the fluids interact pos and remove arg0 and arg1 fluids.
    event.createForFluid('kubejs:test_fluid_1', 'kubejs:test_fluid_2', 'minecraft:lava')

    //create explosion whose mode you defined on the fluids interact pos.
    event.createForExplosion('kubejs:test_fluid_1', 'minecraft:water', 'tnt')

    //summon entity on the fluids interact pos and remove arg0 fluid.
    event.createForEntity('kubejs:test_fluid_1', 'minecraft:water', 'minecraft:zombie')
})
