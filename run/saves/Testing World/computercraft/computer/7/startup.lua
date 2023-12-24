computer = peripheral.find("computer")

while true do
    if (rs.getInput("front")) then
        computer.reboot()
        sleep(0.1)
        computer.turnOn()
    end
    sleep(0.1)
end
