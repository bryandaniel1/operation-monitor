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
 * Defines the view for stock history results.
 */
class HistoryDisplay extends React.Component {

    /**
     * Sets the initial states for this class and sets method bindings.
     * 
     * @param {Object} props The properties passed in from the parent
     */
    constructor(props) {
        super(props);
    }

    /**
     * Executed after the line chart element is ready to be populated with data.
     */
    componentDidMount() {
        const displayData = this.props.data;
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'X');
        data.addColumn('number', displayData.name);

        var historyNodes = displayData.history;
        const keys = Object.keys(historyNodes);
        const rows = keys.map(key => ([key, parseFloat(historyNodes[key].close)]));

        data.addRows(rows.reverse());

        var options = {
            hAxis: {
                title: 'Time'
            },
            vAxis: {
                title: 'Price'
            },
            colors: ['#097138']
        };

        var chart = new google.visualization.LineChart(document.getElementById('lineChart'));
        chart.draw(data, options);
    }

    render() {

        return (
            <div>
                {this.props.data &&
                    <section id="chartSection">
                        <h3>Stock History</h3>
                        <div id="lineChart"></div>
                    </section>
                }
            </div>
        );
    }
}
