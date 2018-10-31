# sim-applet-data-heartbeat
JavaCard SIM card applet to monitor the status of data connectivity over-the-air

## Table of Contents

- [Description](#description)
- [To Do](#todo)
- [Installation and usage](#installation)
- [Contributing and license](#contributing)
- [Standards](#standards)

## Description

GSM SIM cards can realize about the status of network regitration (see ["Location Status" ETSII TS 102.223](https://www.etsi.org/deliver/etsi_ts/102200_102299/102223/12.01.00_60/ts_102223v120100p.pdf). What a SIM cannot realize is whether the data connection has been correctly acquired or not). This projects aim to deliver a "hello-im-alive" heart-beat-kind message to a remote server with the content of a Location Status message.

The document [ETSI TS 143 019 V5.6.0 Annex D](https://www.etsi.org/deliver/etsi_ts/143000_143099/143019/05.06.00_60/ts_143019v050600p.pdf) shows an example of the proactive command OPEN CHANNEL. Here we try to use that example.



## ToDo

The main problem right now is the OPEN CHANNEL command thows a ToolkitException (0x0F COMMAND_NOT_ALLOWED). Since the phone supports OPEN CHANNEL (and actually there's no communication between phone and SIM when this happens), it is assumed there is a missconfiguration of the parameters or the SIM Toolkit Framework installed in the SIM Card doesn't support OPEN CHANNEL.

The former seems more reasonable. But the lack of debug information makes really difficult to find a proper configuration.

- Fix the OPEN CHANNEL failure.

## Installation

To install the applet you need:
- sysmoUSIM-SJS1 SIM + USIM Card with ADM1 key (find it here: http://shop.sysmocom.de/products/sysmousim-sjs1).
- a card reader.
- The scripts for installation are in the osmocom [repository](http://git.osmocom.org/sim/sim-tools/) and you have a guide [here](https://osmocom.org/projects/sim-toolkit/wiki)


## Contributing

Please contribute using [Github Flow](https://guides.github.com/introduction/flow/). Create a branch, add commits, and [open a pull request](https://github.com/fraction/readme-boilerplate/compare/).

Please note this source code has been released under the GPLv3 terms and all contributions will be considered. Have a look at the LICENSE file distributed with this code.

- [Standards](#standards)

[ETSI TS 102 223 Card Application Toolkit (CAT)](https://www.etsi.org/deliver/etsi_ts/102200_102299/102223/14.00.00_60/ts_102223v140000p.pdf)

[ETSI TS 123 107 Quality of Service (QoS) concept and architecture](https://www.etsi.org/deliver/etsi_ts/123100_123199/123107/10.01.00_60/ts_123107v100100p.pdf)