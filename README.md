# sim-applet-data-heartbeat
JavaCard SIM card applet to monitor the status of data connectivity over-the-air

## Table of Contents

- [Description](#description)
- [To Do](#todo)
- [Installation and usage](#installation)
- [Contributing and license](#contributing)


## Description

The purpouse of this applet is to send a heartbeat message throught data connectivity. [ETSI TS 143 019 V5.6.0 Annex D](https://www.etsi.org/deliver/etsi_ts/143000_143099/143019/05.06.00_60/ts_143019v050600p.pdf) shows an example of the proactive command OPEN CHANNEL. Here we try to use that example. 


## ToDo

- To find information to configure the OPEN CHANNEL parameters

## Installation

To install the applet you need:
- sysmoUSIM-SJS1 SIM + USIM Card with ADM1 key (find it here: http://shop.sysmocom.de/products/sysmousim-sjs1).
- a card reader.
- The scripts for installation are in the osmocom [repository](http://git.osmocom.org/sim/sim-tools/) and you have a guide [here](https://osmocom.org/projects/sim-toolkit/wiki)

## Contributing

Please contribute using [Github Flow](https://guides.github.com/introduction/flow/). Create a branch, add commits, and [open a pull request](https://github.com/fraction/readme-boilerplate/compare/).

Please note this source code has been released under the GPLv3 terms and all contributions will be considered. Have a look at the LICENSE file distributed with this code.
