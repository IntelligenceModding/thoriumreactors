reactor = peripheral.find("thorium_reactor")
monitor = peripheral.find("monitor")

function getTableSize(t)
    local count = 0
    for _, __ in pairs(t) do
        count = count + 1
    end
    return count
end

while true do
    monitor.clear()

    monitor.setCursorPos(1,1)
    monitor.write("Assembled: " .. tostring(reactor.isAssembled()))
    
    monitor.setCursorPos(1,2)
    monitor.write("Reactor Active: " .. tostring(reactor.isReactorActive()))
 
    monitor.setCursorPos(1,3)
    monitor.write("Turbine Count: " .. reactor.getTurbineCount())

    monitor.setCursorPos(1,4)
    monitor.write("Turbine Active: " .. tostring(reactor.isTurbineActive()))
 
    for k, v in pairs(reactor.getTurbinePositions()) do
        print("ss")
        print(v)
    end

    sleep(2)
end