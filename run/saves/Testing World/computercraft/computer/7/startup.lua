reactor = peripheral.find("thorium_reactor")
computer = peripheral.find("computer")
mon = peripheral.find("monitor")
w, h = mon.getSize()

local fuelRodStatus = {}
local controlRodStatus = {}
local turbines, turbinesBackup = {}, {}
local reactorCurrentTemperature, reactorTargetTemperature = 0, 0
local reactorFluidAmountIn, reactorFluidCapacityIn = 0, 0
local initXSize, initYSize = 0, 0
local reactorState = ""
local reactorPercent, reactorLoad, turbineCount = 0, 0, 0

function updateData()
    fuelRodStatus = {}
    controlRodStatus = {}
    for k, v in pairs(reactor.getFuelRodStatusMap()) do
        fuelRodStatus[k - 1] = v
    end
    for k, v in pairs(reactor.getCurrentControlRodStatusMap()) do
        controlRodStatus[k - 1] = v
    end

    reactorState = reactor.getReactorState()
    reactorPercent = reactor.getReactorStatusPercent()
    reactorCurrentTemperature = reactor.getReactorCurrentTemperature()
    reactorTargetTemperature = reactor.getReactorTargetTemperature()
    reactorFluidAmountIn = reactor.getReactorFluidAmountIn()
    reactorFluidCapacityIn = reactor.getReactorFluidCapacityIn()
    reactorLoad = reactor.getReactorLoad()
    turbineCount = reactor.getTurbineCount()

    turbinesBackup = {}
    turbinesBackup = turbines

    turbines = {}
    for i = 1, turbineCount do
        turbines[i] = {reactor.getTurbineSpeed(i - 1), reactor.getTurbineGeneration(i - 1), reactor.getTurbineCurrentFlow(i - 1), reactor.isTurbineActive(i - 1)}
    end

    if next(turbinesBackup) == nil then
        turbinesBackup = turbines
    end
end

function labelColor(x, y, text, color)
    mon.setCursorPos(x, y)
    mon.setTextColor(color)
    mon.write(text)
    mon.setTextColor(colors.white)
end

function label(x, y, text)
    mon.setCursorPos(x, y)
    mon.write(text)
end

function labelMils(x, y, text, suffix)
    mon.setCursorPos(x, y)
    mon.write(string.format("%.2f", text) .. suffix)
end

function labelNoMils(x, y, text, suffix)
    mon.setCursorPos(x, y)
    mon.write(string.format("%.0f", text) .. suffix)
end

function drawPixel(x, y, color, char) 
    mon.setTextColor(color)
    
    mon.setCursorPos(x, y)
    mon.write(char)

    mon.setTextColor(colors.white)
end

function drawBackgroundPixel(x, y, color) 
    mon.setBackgroundColor(color)
    mon.setCursorPos(x, y)
    mon.write(" ")
    mon.setBackgroundColor(colors.black)
end

function drawProgress(x, y, width, percentage, color) 
    mon.setBackgroundColor(colors.gray)
    mon.setCursorPos(x, y)
    for i = 0, width do
        mon.write(" ")
    end
    
    mon.setBackgroundColor(color)
    mon.setCursorPos(x, y)
    bar = width / 100 * percentage
    if (bar > width + 0.01) then
        bar = width
        if (percentage <= 105) then
            mon.setBackgroundColor(colors.brown)
        else
            mon.setBackgroundColor(colors.red)
        end
    end 
    for i = 0, bar do
        mon.write(" ")
    end

    mon.setBackgroundColor(colors.black)
end

function drawBox(xMin, xMax, yMin, yMax, title, bcolor, tcolor)
    mon.setBackgroundColor(bcolor)
    for xPos = xMin, xMax, 1 do
        mon.setCursorPos(xPos, yMin)
        mon.write(" ")
    end
    for yPos = yMin, yMax, 1 do
        mon.setCursorPos(xMin, yPos)
        mon.write(" ")
        mon.setCursorPos(xMax, yPos)
        mon.write(" ")
    end
    for xPos = xMin, xMax, 1 do
        mon.setCursorPos(xPos, yMax)
        mon.write(" ")
    end
    mon.setCursorPos(xMin+2, yMin)
    mon.setBackgroundColor(colors.black)
    mon.setTextColor(tcolor)
    mon.write(" ")
    mon.write(title)
    mon.write(" ")
    mon.setTextColor(colors.white)
end

function renderCore()
    for i = 0, 80 do -- fuel rods
        local row = math.floor(i / 9)
        local color = colors.gray
        if (fuelRodStatus[i] == nil) then
            fuelRodStatus[i] = 0
        end
        if (fuelRodStatus[i] >= 100) then
            color = colors.orange
        elseif (fuelRodStatus[i] >= 75) then
            color = colors.magenta
        elseif (fuelRodStatus[i] >= 50) then
            color = colors.lightBlue
        elseif (fuelRodStatus[i] >= 25) then
            color = colors.yellow
        end
        
        drawPixel(w / 2 + (i * 1) - (row * 8 + row * 2), h / 2 - 8 + (i * 1) - (row * 8), color, "o")
    end
    
    for i = 0, 63 do -- control rods
        local row = math.floor(i / 8)
        local color = colors.gray
        if (controlRodStatus[i] == nil) then
            controlRodStatus[i] = 0
        end
        if (controlRodStatus[i] >= 100) then
            color = colors.lime
        elseif (controlRodStatus[i] >= 75) then
            color = colors.pink
        elseif (controlRodStatus[i] >= 50) then
            color = colors.lightGray
        elseif (controlRodStatus[i] >= 25) then
            color = colors.cyan
        end
    
        drawPixel(w / 2 + (i * 1) - (row * 7 + row * 2), h / 2 - 7 + (i * 1) - (row * 7), color, "x")
    end
end

-- Preparation
term.redirect(mon)
mon.clear()
mon.setTextColor(colors.white)
mon.setBackgroundColor(colors.black)
mon.setTextScale(0.5)

-- Color override
term.setPaletteColor(colors.orange, 0x00A90B) -- green 100
term.setPaletteColor(colors.magenta, 0x199021) -- green 75
term.setPaletteColor(colors.lightBlue, 0x337637) -- green 50
term.setPaletteColor(colors.yellow, 0x4C5D4D) -- green 25
term.setPaletteColor(colors.lime, 0x00A6A9) -- blue 100
term.setPaletteColor(colors.pink, 0x198E90) -- blue 75
term.setPaletteColor(colors.lightGray, 0x337576) -- blue 50
term.setPaletteColor(colors.cyan, 0x4C5D5D) -- blue 25
term.setPaletteColor(colors.gray, 0x545454) -- gray
term.setPaletteColor(colors.red, 0xFF0C24) -- red
term.setPaletteColor(colors.brown, 0xFFE124) -- yellow
term.setPaletteColor(colors.purple, 0x878787) -- light gray

mon.setCursorPos(w / 2 - 10, h / 2)
initXSize, initYSize = mon.getSize();
print(initXSize)
if initXSize < 100 or initYSize < 38 then
    mon.setCursorPos(w / 2 - 25, h / 2)
    mon.write("Monitor horizontal size must be atleast bigger 100px (5 blocks)")
    mon.setCursorPos(w / 2 - 25, h / 2)
    mon.write("Monitor vertical size must be atleast bigger 38px (3 blocks)")
    for i = 4, 0, -1 do
        mon.setCursorPos(w / 2 - 8, h / 2 + 1)
        mon.write("Starting in " .. i)
        sleep(1)
    end
end
mon.setCursorPos(w / 2 - 10, h / 2)
mon.write("Updating data...")
updateData()
mon.clear()

-- Monitor design & text
drawBox(1, w, 1, h, "\187 Overview", colors.gray, colors.white)
drawBox(4, 33, 4, h - 3, "\187 Reactor", colors.gray, colors.white)
drawBox(35, w - 35, 4, h - 3, "\187 Core", colors.gray, colors.white)
drawBox(w - 33, w - 4, 4, h - 3, "\187 Turbine", colors.gray, colors.white)

renderCore()

labelColor(7, 7, "Running:", colors.purple)
label(7, 8, "Status:")
label(7, 9, "Repair:")
label(7, 10, "Generating:")

label(7, 14, "Temp:")
label(7, 17, "Load:")
label(7, 20, "Rod Insert:")

label(7, 29, "Uranium:")
label(7, 32, "Fuel:")

label(39, h - 8, "100%")
label(39, h - 7, "75%")
label(39, h - 6, "50%")
label(39, h - 5, "25%")

offset = 0
for i = reactor.getTurbineCount() - 1, 0, -1 do
    label(w - 31, 7 + offset, "Turbine #" .. i)
    offset = offset + 4
end

while true do    
    -- Monitor size changed check
    local xSize, ySize = mon.getSize();
    if initXSize < xSize or initXSize > xSize or initYSize < ySize or initYSize > ySize then
       os.reboot()
    end

    -- Core
    renderCore()

    -- Running
    hours = reactor.getReactorRunningSince() / 20 / 60 / 60
    label(20, 7, math.floor(hours) .. "hrs " .. math.floor((hours - math.floor(hours)) * 60) .. "min   ")

    -- Status
    if (reactorState == "stop") then
        mon.setTextColor(colors.red)
        label(20, 8, "Inactive ")
    else 
        mon.setTextColor(colors.orange)
        label(20, 8, "Active   ")
    end
    mon.setTextColor(colors.white)

    -- Repair
    if (reactorPercent <= 99.5) then
        mon.setTextColor(colors.red)
    elseif (reactorPercent < 100) then
        mon.setTextColor(colors.brown)
    else
        mon.setTextColor(colors.orange)
    end
    labelMils(20, 9, reactorPercent, " %  ")
    mon.setTextColor(colors.white)

    -- Generating
    if (reactorState == "running") then
        mon.setTextColor(colors.orange)
        label(20, 10, "Yes ")
    else
        mon.setTextColor(colors.red)
        label(20, 10, "No  ")
    end
    mon.setTextColor(colors.white)

    -- Bar Texts
    rodInsert = 0
    for i = 0, 63 do
        rodInsert = rodInsert + controlRodStatus[i]
    end
    fuelInsert = 0
    for i = 0, 80 do
        fuelInsert = fuelInsert + fuelRodStatus[i]
    end
    labelMils(20, 14, reactorCurrentTemperature, " °C  ") -- Temp
    labelMils(20, 17, reactorLoad, " %  ")
    labelMils(20, 20, rodInsert / 6400 * 100, " %  ")

    labelMils(20, 29, fuelInsert / 8100 * 100," %  ")
    labelMils(20, 32, reactorFluidAmountIn, " mB  ")

    -- Bars
    drawProgress(7, 15, 23, reactorCurrentTemperature / reactorTargetTemperature * 100, colors.orange) -- Temp
    drawProgress(7, 18, 23, reactorLoad, colors.orange) -- Load
    drawProgress(7, 21, 23, rodInsert / 6400 * 100, colors.lime) -- Rod Insert

    drawProgress(7, 30, 23, fuelInsert / 8100 * 100, colors.lightBlue) -- Uranium
    drawProgress(7, 33, 23, reactorFluidAmountIn / reactorFluidCapacityIn * 100, colors.brown) -- Fuel

    -- Pixels
    drawBackgroundPixel(37, h - 8, colors.orange)
    drawBackgroundPixel(37, h - 7, colors.magenta)
    drawBackgroundPixel(37, h - 6, colors.lightBlue)
    drawBackgroundPixel(37, h - 5, colors.yellow)
    drawBackgroundPixel(38, h - 8, colors.lime)
    drawBackgroundPixel(38, h - 7, colors.pink)
    drawBackgroundPixel(38, h - 6, colors.lightGray)
    drawBackgroundPixel(38, h - 5, colors.cyan)

    -- Turbines
    offset = 0;
    for i = 0, reactor.getTurbineCount() - 1, 1 do
        
        labelMils(w - 30, 8 + offset, turbines[i + 1][1], " RPM")

        local down = turbinesBackup[i +  1][1] < turbines[i +  1][1]
        local same = turbinesBackup[i +  1][1] == turbines[i +  1][1]
        labelColor(w - 18, 8 + offset, (down and "\24" or (same and "-" or "\25")), (down and colors.orange or (same and colors.brown or colors.red)))
        
        labelNoMils(w - 16, 8 + offset, turbines[i + 1][2], " FE/t ")
        mon.setTextColor(colors.red)
        if turbines[i + 1][4] then
            mon.setTextColor(colors.orange)
        end
        label(w - 14, 7 + offset, tostring(turbines[i + 1][4] and "Active    " or "Inactive "))
        mon.setTextColor(colors.white)
        labelNoMils(w - 30, 9 + offset, turbines[i + 1][3], " mB/t ")
        
        offset = offset + 4
    end

    sleep(0.05)
    
    updateData()

end