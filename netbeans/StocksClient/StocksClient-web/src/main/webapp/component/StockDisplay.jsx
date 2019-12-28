/* 
 * Copyright 2019 Bryan Daniel.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Defines the view for stock price results.
 */
class StockDisplay extends React.Component {

    /**
     * Sets the initial states for this class and sets method bindings.
     * 
     * @param {Object} props The properties passed in from the parent
     */
    constructor(props) {
        super(props);
    }

    render() {

        const displayData = this.props.data;

        return (
            <div>
                {this.props.data &&
                    <section>
                        <h3>Stock Data</h3>
                        <ReactBootstrap.Table striped bordered hover>
                            <thead>
                                <tr>
                                    <th>Stock Information</th>
                                    <th>Value</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>Stock Symbol:</td>
                                    <td>{displayData.data[0].symbol}</td>
                                </tr>
                                <tr>
                                    <td>Name:</td>
                                    <td>{displayData.data[0].name}</td>
                                </tr>
                                <tr>
                                    <td>Price:</td>
                                    <td>{displayData.data[0].price}</td>
                                </tr>
                                <tr>
                                    <td>Currency:</td>
                                    <td>{displayData.data[0].currency}</td>
                                </tr>
                                <tr>
                                    <td>Price Open:</td>
                                    <td>{displayData.data[0].price_open}</td>
                                </tr>
                                <tr>
                                    <td>Day High:</td>
                                    <td>{displayData.data[0].day_high}</td>
                                </tr>
                                <tr>
                                    <td>Day Low:</td>
                                    <td>{displayData.data[0].day_low}</td>
                                </tr>
                                <tr>
                                    <td>Day Change:</td>
                                    <td>{displayData.data[0].day_change}</td>
                                </tr>
                                <tr>
                                    <td>Percentage Change:</td>
                                    <td>{displayData.data[0].change_pct}</td>
                                </tr>
                            </tbody>
                        </ReactBootstrap.Table>
                    </section>
                }
            </div>
        );
    }
}
